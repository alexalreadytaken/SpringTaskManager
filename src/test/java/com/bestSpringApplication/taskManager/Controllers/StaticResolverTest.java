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
import java.util.HashMap;
import java.util.Map;

import static com.bestSpringApplication.taskManager.Controllers.UsersHandler.*;
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
    @Autowired
    private UsersHandler usersHandler;

    @BeforeAll
    static void start() {
        LOGGER.info("START GET MAPPINGS TEST");
    }

    @Test
    public void loginTest() throws Exception {
        LOGGER.debug("Preparing users to db");
        usersHandler.prepare();

        clientLogin(existAdmin.getMail(),existAdmin.getPassword(),"/home");
        clientLogin(notExistUser.getMail(),notExistUser.getPassword(),"/login?error");
        clientLogin(existUser.getMail(),existUser.getPassword(),"/home");
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
        clientGetMapping("/home",status().isOk(), (MockHttpSession) SESSIONS.get(existUser.getMail()));
        clientGetMapping("/home",status().isUnauthorized(), (MockHttpSession) SESSIONS.get(notExistUser.getMail()));
        clientGetMapping("/home",status().isOk(), (MockHttpSession) SESSIONS.get(existAdmin.getMail()));
        clientGetMapping("/randomUrl",status().isNotFound(), (MockHttpSession) SESSIONS.get(existAdmin.getMail()));
    }
    @Test
    public void rolesGetMappingTests() throws Exception {
        clientGetMapping("/admin",status().isForbidden(), (MockHttpSession) SESSIONS.get(existUser.getMail()));
        clientGetMapping("/admin",status().isUnauthorized(), (MockHttpSession) SESSIONS.get(notExistUser.getMail()));
        clientGetMapping("/admin",status().isOk(), (MockHttpSession) SESSIONS.get(existAdmin.getMail()));

        LOGGER.debug("Remove users from db");
        usersHandler.curlUp();
    }

    @AfterAll
    static void end() {
        LOGGER.info("END GET MAPPINGS TEST");
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

}
