package com.bestSpringApplication.taskManager.Controllers;

import com.bestSpringApplication.taskManager.models.user.UserModel;
import com.bestSpringApplication.taskManager.servises.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc client;

    private final UserModel existUser = new UserModel("test-1","test-1","test-1","USER");
    private final UserModel notExistUser = new UserModel("test-2","test-2","test-2","USER");

    @BeforeEach
    public void start(){
        try {
            userService.deleteUser((UserModel) userService.loadUserByUsername(notExistUser.getMail()));
            userService.deleteUser((UserModel) userService.loadUserByUsername(existUser.getMail()));
        }catch (UsernameNotFoundException ignored){}
        userService.saveUser(existUser);
    }

    @Test
    public void testRegistration() throws Exception {
        register(existUser,false);
        register(notExistUser,true);
    }
    private ResultActions register(UserModel user, boolean expectedResponse) throws Exception {
        return this.client.perform(post("/reg")
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("{\"name\":\"%s\",\"mail\":\"%s\",\"password\":\"%s\"}"
                ,user.getName(),user.getMail(),user.getPassword())))
            .andExpect(content().json(String.format("{\"register\":%b}",expectedResponse)))
            .andDo(print());
    }

    @AfterEach
    public void end(){
        try {
            userService.deleteUser((UserModel) userService.loadUserByUsername(notExistUser.getMail()));
            userService.deleteUser((UserModel) userService.loadUserByUsername(existUser.getMail()));
        }catch (UsernameNotFoundException ignored){}
    }
}