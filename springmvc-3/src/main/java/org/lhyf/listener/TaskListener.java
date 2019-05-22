package org.lhyf.listener;

import org.lhyf.entity.RequestEntity;
import org.lhyf.util.DeferredResultMap;
import org.lhyf.util.ProtostuffSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/****
 * @author YF
 * @date 2019-04-08 16:06
 * @desc TaskListener
 *
 *  异步处理处理思路:
 *  1.Controller 中接受到请求同时使用Redis(incr)生成一个序列号,然后将请求参数和序列号封装到一个对象中,并持久化到Redis队列,
 *    创建一个ConcurrentHashMap(单例) 用来存储序列号,和对应的DeferredResult.
 *  2.使用监听器,监听spring 容器刷新事件,事件触发,创建一个线程来阻塞读取(brpop)队列
 *
 *
 *
 *  注意:如果并发量确实太高会导致,会加入到队列中的任务太多,后台线程处理不过来,
 *  此时队列中大部分任务因为超时已经响应失败,而读取队列的线程无法感知已经响应失败的任务,
 *  这将导致任务处理线程还在继续处理已经响应了的任务,而新请求的任务依然排在队列尾端,从而
 *  导致这个任务又将被响应超时。
 *
 **/
@Component
public class TaskListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private DeferredResultMap deferredResultMap;

    @Autowired
    private ExecutorService threadPool;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 如果是Spring发布的事件,不做处理,不然会收到两次事件
        if (event.getApplicationContext().getParent() == null) {
            return;
        }
        System.out.println("收到事件..." + event);

        new Thread(new Runnable() {
            String uuid = null;
            Long serial = null;

            @Override
            public void run() {
                try {
                    Jedis jedis = jedisPool.getResource();
                    List<byte[]> list = null;
                    while (true) {
                        list = jedis.brpop(0, "task1".getBytes()); // 如果没有内容,将被阻塞
                        RequestEntity entity = null;

                        // list 的第一个元素是key的字节数组,第二个才是pop弹出的值
                        if (list.size() == 2 && list.get(1).length > 0) {
                            byte[] bytes = list.get(1);
                            entity = ProtostuffSerializer.deserialize(bytes);
                            System.out.println(entity);
                            serial = entity.getSerial();
                            if (serial != null) {
                                threadPool.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            DeferredResult<Object> result = deferredResultMap.get(String.valueOf(serial));
                                            if (result != null) {
                                                uuid = UUID.randomUUID().toString();
                                                deferredResultMap.remove(String.valueOf(serial));
                                                result.setResult(uuid);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "task1").start();
    }
}
