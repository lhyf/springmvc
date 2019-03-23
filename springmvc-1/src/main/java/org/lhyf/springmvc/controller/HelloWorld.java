package org.lhyf.springmvc.controller;

import org.lhyf.springmvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {

    private String msg;

    @ResponseBody
    @RequestMapping("/set/msg")
    public String setMsg(String msg) {
        System.out.println("set:" + Thread.currentThread().getName());
        this.msg = msg;
        return msg;
    }

    @ResponseBody
    @RequestMapping("/get/msg")
    public String getMsg() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("get" + Thread.currentThread().getName());
        return this.msg;
    }

    @RequestMapping("/index")
    public String hello() {
        return "success";
    }

    @RequestMapping("/test/databinder")
    public String testDataBinder(User user){
        System.out.println(user);
        return "success";
    }
}

