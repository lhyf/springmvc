package org.lhyf.controller;

import org.lhyf.entity.RequestEntity;
import org.lhyf.service.LoginService;
import org.lhyf.util.DeferredResultMap;
import org.lhyf.util.ProtostuffSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/****
 * @author YF
 * @date 2019-03-26 23:56
 * @desc IndexController
 *
 * 异步处理的使用场景分两种,一种是客户端只需要触发就行,不用等待长时间处理后的结果;
 * 另一种是用户需要等待长时间处理后的结果
 * 1.针对于第一种,可以开启新的线程处理耗时的工作,同步响应用户ok状态
 *
 * 2.用户需要等待结果,则需要使用Servlet3.0的异步机制处理,如下面的异步处理
 **/
@Controller
public class IndexController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JedisPool jedisPool;


    @Autowired
    private DeferredResultMap deferredResultMap;

    @RequestMapping(value = {"", "/"})
    public ModelAndView index() {
        System.out.println(Thread.currentThread().getName());
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("msg", loginService.sayHi("tomcat"));
        return mv;
    }

    /**
     * 使用异步响应
     * Callable 处理：
     * 控制器返回一个Callable。
     * Spring MVC调用request.startAsync()并将其提交Callable到一个 TaskExecutor以在单独的线程中进行处理。
     * 同时DispatcherServlet和所有的Filter都退出Servlet容器线程，但响应仍保持打开状态。
     * 最终Callable生成一个结果，Spring MVC将请求调度回Servlet容器以完成处理。
     * 根据Callable返回的结果。SpringMVC继续进行视图渲染流程等（从收请求-视图渲染）。
     *
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/callable")
    public Callable<String> processSth(@RequestParam("name") String name) {
        System.out.println("主线程开始:" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程开始:" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("子线程结束:" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
                return "Hi " + name;
            }
        };
        System.out.println("主线程结束:" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        return callable;
    }

    /**
     * 发起订单生成请求,交由其他线程来处理
     * 控制器返回一个 DeferredResult ,同时将它保存在内存队列或集合中.
     * SpringMVC 调用request.startAsync()
     * 同时DispatcherServlet和所有配置的Filter将退出请求处理的线程,但是响应的Response依然保持打开状态
     * 其他线程处理完DeferredResult的返回结果后,SpringMVC再次转发此次请求到Servlet容器
     * DispatcherServlet 再次被调用,并恢复处理异步产生的结果值
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/create-order")
    public DeferredResult<Object> createOrder() {
        DeferredResult<Object> result = new DeferredResult<>(400L, "create fail...");
        RequestEntity entity = new RequestEntity();
        entity.setNumber("123456");
        entity.setName("name");

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 获取唯一的序列号,如果序列号错误,将会导致A请求结果可能是其他请求的响应.
            Long incr = jedis.incr("taskline1");
            entity.setSerial(incr);
            byte[] bytes = ProtostuffSerializer.serialize(entity);
            jedis.lpush("task1".getBytes(), bytes);

            result.onTimeout(new Runnable() {
                @Override
                public void run() {
                    deferredResultMap.remove(String.valueOf(incr));
                }
            });
            result.onCompletion(new Runnable() {
                @Override
                public void run() {
                    deferredResultMap.remove(String.valueOf(incr));
                }
            });

            deferredResultMap.put(String.valueOf(incr), result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
//        deferredResultQueue.save(result);
        return result;
    }


    @ResponseBody
    @RequestMapping("/get-size")
    public String getMapSize() {
        return deferredResultMap.size() + ":";
    }

    @ResponseBody
    @RequestMapping("/get-all-map")
    public Map getAllMap() {
        return deferredResultMap.getMap();
    }

    /**
     * 模拟另外一个线程来生成订单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/process-order")
    public String processOrder() {
        String uuid = UUID.randomUUID().toString();
        Jedis jedis = null;
        Long serial = null;
        try {
            jedis = jedisPool.getResource();
            byte[] bytes = jedis.rpop("task1".getBytes());
            RequestEntity entity = ProtostuffSerializer.deserialize(bytes);
            serial = entity.getSerial();
            String name = entity.getName();
            String number = entity.getNumber();
            System.out.println(name + ":" + number);
            DeferredResult<Object> result = deferredResultMap.get(String.valueOf(serial));
            result.setResult(uuid);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            deferredResultMap.remove(String.valueOf(serial));
            if (jedis != null) {
                jedis.close();
            }
        }
        return uuid;
    }

    @ResponseBody
    @RequestMapping("/call-back")
    public DeferredResult<Object> callBack() {
        // 4秒后未响应，则返回 create fail...
        DeferredResult<Object> result = new DeferredResult<>(4000L, "create fail...");
        result.onCompletion( () -> System.out.println("Processing complete..."));
        result.onTimeout(() -> result.setErrorResult("Request timeout..."));
        result.onError((Throwable t) -> {result.setErrorResult("Request error...");});
        return result;
    }
}
