package org.lhyf.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/****
 * @author YF
 * @date 2018-06-17 11:07
 * @desc UnauthorizedException
 *
 **/
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "未授权访问！")
public class UnauthorizedException extends RuntimeException {
}
