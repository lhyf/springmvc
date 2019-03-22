package org.lhyf.springmvc.controller;

import org.lhyf.springmvc.exception.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Past;

/****
 * @author YF
 * @date 2018-06-17 10:23
 * @desc ExceptionController
 *
 **/
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/testResponseStatus")
    public String testResponseStatus(@RequestParam("username") String username,@RequestParam("password") String password){
        if("admin".equals(username) && !"123456".equals(password)){
            throw new UnauthorizedException();
        }
        return "success";
    }

    /**
     * 1.在 @ExceptionHandler 方法的入参中可以加入Exception 类型的参数,该参数即放生的异常对象
     * 2.在@ExceptionHandler 方法的入参中不能传入Map ,若希望把异常信息传导到页面上,需要使用ModelAndView 作为返回值
     * 3. @ExceptionHandler 方法标记的异常有优先级的问题. 匹配更精确的
     * 4. @ControllerAdvice:如果在当前Handler 中找不到 @ExceptionHandler 标记的方法来处理当前出现的异常,将去  @ControllerAdvice
     * 标记的类中查找 @ExceptionHandler 标记的方法来处理异常.
     *
     * @param e
     * @return
     */
//    @ExceptionHandler({ArithmeticException.class})
//    public ModelAndView handlerArithmeticException(Exception e) {
//        ModelAndView mv = new ModelAndView("error");
//        mv.addObject("exception:", e);
//
//        return mv;
//    }

    @RequestMapping("/testexception")
    public String testException(@RequestParam("i") int i) {
        System.out.println(10 / i);
        return "success";
    }
}
