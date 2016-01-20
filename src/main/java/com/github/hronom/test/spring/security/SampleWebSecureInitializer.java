package com.github.hronom.test.spring.security;

import com.github.hronom.test.spring.security.configs.ApplicationSecurityConfig;
import com.github.hronom.test.spring.security.configs.WebMvcConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SampleWebSecureInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the dispatcher servlets Spring application context.
        AnnotationConfigWebApplicationContext dispatcherContext =
            new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebMvcConfig.class);
        dispatcherContext.register(ApplicationSecurityConfig.class);

        // Register servlet.
        ServletRegistration.Dynamic servletRegistration =
            servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/*");
    }
}
