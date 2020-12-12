package com.bestSpringApplication.taskManager.Controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StaticResolverTest {

    private final Map<String,HttpSession> sessions = new HashMap<>();

    @Autowired
    private MockMvc client;

    @BeforeEach
    public void start() throws Exception {
        clientLogin("admin","admin","/home");
        clientLogin("unregistered","unregistered","/login?error");
        clientLogin("user","user","/home");
    }

    @Test
    public void unauthorizedGetMappingTests() throws Exception {
        clientGetMapping("/login",status().isOk());
        clientGetMapping("/register",status().isOk());

        clientGetMapping("/",status().isUnauthorized());
        clientGetMapping("/home",status().isUnauthorized());
        clientGetMapping("/admin",status().isUnauthorized());
        clientGetMapping("/study",status().isUnauthorized());

        clientGetMapping("/randomUrl",status().isUnauthorized());
    }
    @Test
    public void authorizedGetMappingTests() throws Exception {
        clientGetMapping("/home",status().isOk(), (MockHttpSession) sessions.get("user"));
        clientGetMapping("/home",status().isUnauthorized(), (MockHttpSession) sessions.get("unregistered"));
        clientGetMapping("/home",status().isOk(), (MockHttpSession) sessions.get("admin"));
        clientGetMapping("/randomUrl",status().isNotFound(), (MockHttpSession) sessions.get("admin"));
    }
    @Test
    public void rolesGetMappingTests() throws Exception {
        clientGetMapping("/admin",status().isForbidden(), (MockHttpSession) sessions.get("user"));
        clientGetMapping("/admin",status().isUnauthorized(), (MockHttpSession) sessions.get("unregistered"));
        clientGetMapping("/admin",status().isOk(), (MockHttpSession) sessions.get("admin"));
    }

    public ResultActions clientLogin(String mail,String password,String redirectUrl) throws Exception {
        return this.client.perform(formLogin().user(mail).password(password))
            .andExpect(redirectedUrl(redirectUrl))
            .andDo(mvcResult->this.sessions.put(mail,mvcResult.getRequest().getSession()))
            .andExpect(status().isFound())
            .andDo(print());
    }

    public ResultActions clientGetMapping(String url, ResultMatcher result,MockHttpSession session) throws Exception {
        return this.client.perform(get(url).session(session))
            .andDo(print())
            .andExpect(result);
    }
    public ResultActions clientGetMapping(String url, ResultMatcher result) throws Exception {
        return this.client.perform(get(url))
            .andDo(print())
            .andExpect(result);
    }

    @AfterEach
    public void end() {
        System.out.println("end");
    }

}