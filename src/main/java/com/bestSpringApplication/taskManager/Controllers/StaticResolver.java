package com.bestSpringApplication.taskManager.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResolver implements WebMvcConfigurer {

    @Value("${xml.task.pool.path}")
    private String taskPoolPath;
    @Value("${js.files.path}")
    private String jsFilesPath;
    @Value("${html.files.path}")
    private String htmlFilesPath;
    @Value("${css.files.path}")
    private String cssFilesPath;
    @Value("${favicon.path}")
    private String faviconPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String file = "file:";
        registry.addResourceHandler("/v/**")
                .addResourceLocations(file.concat(htmlFilesPath));
        registry.addResourceHandler("/js/**")
                .addResourceLocations(file.concat(jsFilesPath));
        registry.addResourceHandler("/css/**")
                .addResourceLocations(file.concat(cssFilesPath));
        registry.addResourceHandler("favicon.ico")
                .addResourceLocations(file.concat(faviconPath));
        registry.addResourceHandler("/schemas/file/**")
                .addResourceLocations(file.concat(taskPoolPath));
    }

}
