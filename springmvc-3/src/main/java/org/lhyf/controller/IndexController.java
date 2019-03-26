package org.lhyf.controller;

import org.lhyf.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/****
 * @author YF
 * @date 2019-03-26 23:56
 * @desc IndexController
 *
 **/
@Controller
public class IndexController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = {"", "/"})
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("msg", loginService.sayHi("tomcat"));
        return mv;
    }
}
