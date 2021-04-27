package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.utils.exceptions.internal.PostConstructInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.stream.Stream;

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

    @PostConstruct
    private void validateDirs(){
        boolean filesValid = Stream.of(new File(taskPoolPath),new File(jsFilesPath),
                new File(htmlFilesPath),new File(cssFilesPath),new File(faviconPath))
                .allMatch(File::exists);
        if (!filesValid){
            throw new PostConstructInitializationException("One of the directories was not found");
        }
    }

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
