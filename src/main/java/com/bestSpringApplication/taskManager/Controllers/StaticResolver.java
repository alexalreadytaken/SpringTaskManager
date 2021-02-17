package com.bestSpringApplication.taskManager.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResolver implements WebMvcConfigurer {

    @Value("${task.pool.path}")
    private String taskPoolPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/").setViewName("redirect:/home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/study").setViewName("study");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
        String filePath = "file:///".concat(taskPoolPath);
        registry.addResourceHandler("/schemas/file/**")
                .addResourceLocations(filePath);
    }

}
