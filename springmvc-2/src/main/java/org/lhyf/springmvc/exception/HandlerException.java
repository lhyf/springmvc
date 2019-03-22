package org.lhyf.springmvc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/****
 * @author YF
 * @date 2018-06-17 10:54
 * @desc HandlerException
 *
 **/

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler({ArithmeticException.class})
    public ModelAndView handlerArithmeticException(Exception e) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception:", e);

        return mv;
    }
}
