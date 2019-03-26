package org.lhyf.service;

import org.springframework.stereotype.Service;

/****
 * @author YF
 * @date 2019-03-27 00:08
 * @desc LoginService
 *
 **/
@Service
public class LoginService {

    public String sayHi(String name){
        return "Hi " + name;
    }
}
