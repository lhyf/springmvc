package org.lhyf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.Executors;

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
//@EnableAsync
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
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new ValidateInterceptor()).addPathPatterns("/**");
    }

    /**
     * 配置异步响应需要的线程池
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
        configurer.setTaskExecutor(executor);
    }
}
