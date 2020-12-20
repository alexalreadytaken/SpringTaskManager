/*
package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.user.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.bestSpringApplication.taskManager.Controllers.UsersHandler.existUser;
import static com.bestSpringApplication.taskManager.Controllers.UsersHandler.notExistUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {


    @Autowired
    private MockMvc client;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersControllerTest.class);



    @BeforeAll
    static void start(){
        LOGGER.info("START USERS CONTROLLER TESTS");
    }

    @Test
    public void testRegistration() throws Exception {
        register(existUser,false);
        register(notExistUser,true);
    }
    private ResultActions register(User user, boolean expectedResponse) throws Exception {
        return this.client.perform(post("/reg")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("{\"name\":\"%s\",\"mail\":\"%s\",\"password\":\"%s\"}"
                ,user.getName(),user.getMail(),user.getPassword())))
            .andExpect(content().json(String.format("{\"register\":%b}",expectedResponse)))
            .andDo(print());
    }

    @AfterAll
    static void end(){
        LOGGER.info("END USERS CONTROLLER TESTS");
    }
}
*/
