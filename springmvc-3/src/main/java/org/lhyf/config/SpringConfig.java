package org.lhyf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/****
 * @author YF
 * @date 2019-03-26 23:45
 * @desc SpringConfig
 *
 * 父容器,扫描Spring管理的
 **/
@ComponentScan(basePackages = "org.lhyf",
        excludeFilters = {@Filter(type = FilterType.ANNOTATION, classes = {Controller.class})}
)
@Configuration
public class SpringConfig {

}