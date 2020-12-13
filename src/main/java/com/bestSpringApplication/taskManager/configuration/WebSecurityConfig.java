package com.bestSpringApplication.taskManager.configuration;


import com.bestSpringApplication.taskManager.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserService userService){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permittedMappings = {"/favicon.ico","/js/**","/register","/reg","/admin/**"};//fixme: remove admin, only for test ng
        http
                    .cors().configurationSource(corsConfig())
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(permittedMappings).permitAll()
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated()
                .and()
                    .httpBasic()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                    .defaultSuccessUrl("/home", true)
                .and()
                    .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(31))
                    .key("superKey")
                .and()
                    .logout().logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    .logoutSuccessUrl("/login");
    }
    @Bean
    CorsConfigurationSource corsConfig(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
        UrlBasedCorsConfigurationSource finalConfig = new UrlBasedCorsConfigurationSource();
        finalConfig.registerCorsConfiguration("/**",config);
        return finalConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
