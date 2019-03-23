package org.lhyf.springmvc.controller;

import org.lhyf.springmvc.entity.Address;
import org.lhyf.springmvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//@SessionAttributes(value = "user",types = {String.class})
@Controller
@RequestMapping("/test")
public class SpringMVCTest {


    @ResponseBody
    @RequestMapping("/list")
    public List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        return list;

    }

    /**
     * 不自动将表单中的值绑定到方法入参.
     *
     * @param binder
     */
//    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("lastName");
    }

    @RequestMapping(value = "/convertor", method = RequestMethod.POST)
    public String testConvertor(User user) {


        System.out.println(user);
        return "success";

    }

    @RequestMapping("/input")
    public String input(User user, Map map) {
        map.put("user", user);
        return "input";
    }

    @RequestMapping(value = "/dataBinding", method = RequestMethod.POST)
    public String testDataBinding(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.getErrorCount() > 0) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + " : " + error.getDefaultMessage());
            }
            // 若验证出错,转向定制的页面

            return "input";
        }

        System.out.println(user);
        return "success";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }

    @RequestMapping("/testView")
    public String testView() {
        System.out.println("testView");
        return "helloView";

    }

    @RequestMapping("/viewresolve")
    public String testViewResolve() {
        return "success";
    }

    /**
     * 1.有 @ModelAttribute 标记的方法,会在每个目标方法执行之前被SpringMVC调用!
     * 2. @ModelAttribute 注解也可以来修饰目标方法的 POJO 类型的入参，其中value 属性值会有如下的作用:
     * 1).SpringMVC 会使用Value 属性值在implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
     * 2). SpringMVC会以value为key ,POJO 类型的对象作为value ,存入到request 中.
     *
     * @param id
     * @param map
     */
//    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) Integer id,
                        Map<String, Object> map) {
        if (id != null) {
            User user = new User(1, "tom", "123456", "tom@163.com", 16);
            System.out.println("从数据库获取User" + user);
            map.put("user", new Address());
        }

    }


    /**
     * 运行流程:
     * 1. 执行@ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放到了Map 中. 键为: user
     * 2. SpringMVC 从Map中取出 User 对象, 并把表单的请求参数赋给该User 对象的对应属性.
     * 3. SpringMVC 把上述对象传入目标方法的参数.
     * <p>
     * 注意: 在 @ModelAttribute 修饰的方法中,放到Map 时的键需要目标方法入参类型的第一个字母小写的字符串一致!
     * <p>
     * SpringMVC 确定目标方法POJO 类型入参的过程.
     * 1. 确定一个Key:
     * 1). 若目标方法的 POJO 类型的参数没有使用 @ModelAttribute 修饰,则 Key 为 POJO 类名第一个字母的小写
     * 2). 若使用了@ModelAttribute 来修饰,则key 为@ModelAttribute 注解的value 属性值.
     * 2. 在implicitModel 中查找key 对应的对象, 若存在, 则作为入参传入
     * 1). 若在@ModelAttribute 标记的方法在Map 中保存过, 且key 和 1 确定的key 一致,则会获取到.
     * 3. 若implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用了 @SessionAttitudes 注解修饰,
     * 若使用了该注解,且 @SessionAttributes 注解的value 属性值中包含了 key, 则会从HttpSession 中获取key 所对应
     * 的value值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常.
     * 4. 若Handler 没有只用@SessionAttributes 注解，或@SessionAttributes 注解的value 值中不包含 key,
     * 则通过反射来创建 目标方法的POJO入参对象，并传入目标方法。
     * 5. SpringMVC 会把Key 和POJO 类型的对象保存到 implicitModel 中， 进而会保存到 request 中.
     * <p>
     * 源代码分析的流程:
     * 1. 调用 @ModelAttribute 注解修饰的方法. 放在Map中的数据实际上是放在 implicitModel 中的.
     * 2. 解析目标方法的参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性.
     * 1). 创建 WebDataBinder 对象.
     * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写.
     * *注意: attrName. 若目标方法的POJO 入参属性使用了 @ModelAttribute 来修饰,则 attrName 值即为
     *
     * @param user
     * @return
     * @ModelAttribute 的 value 属性值.
     * <p>
     * ②. 确定target 属性:
     * > 在implicitModel 中查找 attrName 对应的属性值. 若存在,则返回该值.
     * > *若不存在: 则验证当前Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
     * 获取 attrName 所对应的属性值,若Session 中没有对应的属性值, 则抛出异常.
     * > 若Handler 没有使用 @SessionAttributes 进行修饰, 则 @SessionAttributes 中没有使用value值指定的key
     * 和attrName 相匹配, 则通过反射创建了入参的POJO对象.
     * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性.
     * 3). * SpringMVC 会把WebDataBinder的 attrName 和 target 给到 implicitModel ,进而传到request  域中.
     * 4). 把WebDataBinder 的target 作为参数传递给目标方法的入参.
     */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute(value = "user") User user) {
        System.out.println("修改：" + user);
        return "success";
    }

    /**
     * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是value 属性值),
     * 还可以通过模型属性的对象类型指定哪些模型需要放到会话中(实际上使用的是 types 属性值)
     * value 通过指定放在map 中键名来识别哪些需要放到session域中,
     * types 指定放在map 中的value ,有哪些类型需要放到session 域中
     * 注意：该注解只能放在类的上面,而不能放在方法的上面
     */
    @RequestMapping("/testSessionAttributes")
    public String testSessionAttributes(Map<String, Object> map) {
        User user = new User(1, "tom", "123456");
        map.put("user", user);
        map.put("address", "liaoning province");
        return "success";
    }

    /**
     * 目标方法可以添加Map 类型(实际上也可以是Model 类型或 ModelMap 类型)的参数
     */
    @RequestMapping("/map")
    public String testMap(Map<String, Object> map) {
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("tom", "jerry", "mike"));
        return "success";
    }

    /**
     * 目标方法的返回值可以是 ModelAndView 类型
     * 其中可以包含视图和模型信息
     * SpringMVC 会把 ModelAndView 的 model 中的数据放到 request 域对象中.
     *
     * @return
     */
    @RequestMapping("/model-and-view")
    public ModelAndView testModelAndView() {
        String viewName = "success";
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject("time", new Date());
        return mv;
    }

    /**
     * 可以使用Servlet原生的API作为目标方法的参数
     * 具体支持以下类型
     * HttpServletRequest
     * HttpServletResponse
     * HttpSession
     * java.security.Principal
     * Locale
     * InputStream
     * OutputStream
     * Reader
     * Writer
     */
    @RequestMapping("/servletapi")
    public void testServletApi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write("Hello SpringMVC");
    }

    @RequestMapping("/pojo")
    public String testPojo(User user) {
        System.out.println(user);
        return "success";
    }

    @RequestMapping("/cookievalue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID", required = false, defaultValue = "") String value) {
        System.out.println("sessionId : " + value);
        return "success";
    }

    /**
     * @RequestParam 映射请求参数.
     * value 值即为请求参数名
     * required 该参数是否必须,默认为true
     * defaultValue 请求参数的默认值
     */
    @RequestMapping("/requestparam")
    public String testRequestParam(@RequestParam("username") String username,
                                   @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        System.out.println(username + " : " + age);
        return "success";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String testDelete(@PathVariable("id") Integer id) {
        System.out.println("delete 请求:" + id);
        return "success";
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT)
    public String testPut(@PathVariable("id") Integer id) {
        System.out.println("put 请求:" + id);
        return "success";
    }

    /**
     * @param userName
     * @return
     * @PathVariable 可以来映射URL 中的占位符到目标方法的参数中.
     */
    @RequestMapping("/pathvariable/{username}")
    public String testPathVariable(@PathVariable("username") String userName) {
        System.out.println(userName);
        return "success";
    }

    /**
     * @return
     * @RequestMapping 除了修饰方法, 还可以修饰类
     * - 类定义处：提供初步的请求映射信息。相对于 WEB 应用的根目录
     * – 方法处：提供进一步的细分映射信息。相对于类定义处的 URL。若
     * 类定义处未标注 @RequestMapping，则方法处标记的 URL 相对于WEB 应用的根目录
     * method: 指定请求方式
     * params:指定请求参数
     * headers:指定请求头信息
     */
    @RequestMapping(value = "/requestmapping", method = RequestMethod.POST,
            params = {"username", "age=10"}, headers = {"Accept-Language: zh-CN,zh;q=0.9"})
    public String testRequestMapping() {
        return "success";
    }
}
