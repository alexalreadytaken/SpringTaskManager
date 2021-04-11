package com.bestSpringApplication.taskManager.configurations;

import com.bestSpringApplication.taskManager.utils.parsers.xml.XmlTaskParser;
import org.jdom2.Element;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {

    @Bean
    @Scope("prototype")
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public XmlTaskParser initTaskParser(Element arg) {
        return new XmlTaskParser(arg);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(15);
    }
}
