package org.lhyf;

import org.lhyf.config.SpringConfig;
import org.lhyf.config.SpringMvcConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/****
 * @author YF
 * @date 2019-03-26 23:40
 * @desc WebAppInitializer
 *
 * 利用Servlet 3.0 规范,将可以不需要web.xml来配置 Servlet,Filter,Listener
 * 所有,Spring 容器和 DispatcherServlet 也可以不用配置在 web.xml 中
 * 只需要继承AbstractAnnotationConfigDispatcherServletInitializer,
 * 并传入Spring和SpringMVC的配置类,以及要拦截的路径即可,
 * 均可以通过注解来配置
 **/
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 传入Spring容器的配置
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringConfig.class};
    }

    /**
     * 传入SpringMVC容器的配置
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    /**
     * DispatcherServlet 拦截的路径,
     * / : 拦截包括静态资源在内,但不包含 *.jsp
     * /* 拦截静态资源,且包含 *.jsp在内
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
