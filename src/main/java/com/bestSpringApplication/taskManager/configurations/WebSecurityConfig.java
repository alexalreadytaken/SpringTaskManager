package com.bestSpringApplication.taskManager.configurations;


import com.bestSpringApplication.taskManager.servises.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull private final PasswordEncoder passwordEncoder;
    @NonNull private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permittedMappings = {"/favicon.ico","/js/**","/register/**","/schemas/**","/admin/**"};
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(permittedMappings).permitAll()
//                    .antMatchers("/schemas/**","/js/private/**")
//                        .hasAnyAuthority(Role.ADMIN.getStrValue(),Role.TEACHER.getStrValue())
//                    .antMatchers("/study/**")
//                        .hasAuthority(Role.STUDENT.getStrValue())
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
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))//need?
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
