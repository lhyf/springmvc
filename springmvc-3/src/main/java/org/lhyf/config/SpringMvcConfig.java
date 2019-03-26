package org.lhyf.config;

import org.lhyf.interceptor.LoginInterceptor;
import org.lhyf.interceptor.ValidateInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/****
 * @author YF
 * @date 2019-03-26 23:46
 * @desc SpringMvcConfig
 *
 **/
@ComponentScan(basePackages = "org.lhyf",
        includeFilters = {@Filter(type = FilterType.ANNOTATION, classes = {Controller.class})},
        useDefaultFilters = false)
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    // 配置 default-servlet-handler ,处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views",".jsp");
    }

    /**
     * 注册拦截器,多个拦截器执行的顺序与再次配置的先后有关
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ValidateInterceptor()).addPathPatterns("/**");
    }


}
