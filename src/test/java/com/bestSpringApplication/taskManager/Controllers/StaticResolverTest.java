package com.bestSpringApplication.taskManager.Controllers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class StaticResolverTest {

    private static final Map<String,HttpSession> SESSIONS = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticResolverTest.class);

    @Autowired
    private MockMvc client;

    @BeforeAll
    static void start() {
        LOGGER.info("START GET_MAPPINGS TEST IN {}",LocalDateTime.now());
    }

    @Test
    public void loginTest() throws Exception {
        clientLogin("1","1","/home");
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
        clientGetMapping("/home",status().isOk(), (MockHttpSession) SESSIONS.get("user"));
        clientGetMapping("/home",status().isUnauthorized(), (MockHttpSession) SESSIONS.get("unregistered"));
        clientGetMapping("/home",status().isOk(), (MockHttpSession) SESSIONS.get("1"));
        clientGetMapping("/randomUrl",status().isNotFound(), (MockHttpSession) SESSIONS.get("1"));
    }
    @Test
    public void rolesGetMappingTests() throws Exception {
        clientGetMapping("/admin",status().isForbidden(), (MockHttpSession) SESSIONS.get("user"));
        clientGetMapping("/admin",status().isUnauthorized(), (MockHttpSession) SESSIONS.get("unregistered"));
        clientGetMapping("/admin",status().isOk(), (MockHttpSession) SESSIONS.get("1"));
    }

    public void clientLogin(String mail,String password,String redirectUrl) throws Exception {
        client.perform(formLogin().user(mail).password(password))
            .andExpect(redirectedUrl(redirectUrl))
            .andDo(mvcResult-> SESSIONS.put(mail,mvcResult.getRequest().getSession()))
            .andExpect(status().isFound());
    }

    public void clientGetMapping(String url, ResultMatcher result,MockHttpSession session) throws Exception {
        client.perform(get(url).session(session))
            .andExpect(result);
    }
    public void clientGetMapping(String url, ResultMatcher result) throws Exception {
        client.perform(get(url))
            .andExpect(result);
    }

    @AfterAll
    static void end() {
        LOGGER.info("END GET_MAPPINGS TEST IN {}",LocalDateTime.now());
    }

}