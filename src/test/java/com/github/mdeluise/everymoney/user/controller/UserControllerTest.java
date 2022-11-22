package com.github.mdeluise.everymoney.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.TestEnvironment;
import com.github.mdeluise.everymoney.authentication.User;
import com.github.mdeluise.everymoney.authentication.UserController;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.security.ApplicationSecurityConfig;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenFilter;
import com.github.mdeluise.everymoney.security.jwt.JwtTokenUtil;
import com.github.mdeluise.everymoney.security.jwt.JwtWebUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

@WebMvcTest(UserController.class)
@Import(
    {
        JwtTokenFilter.class,
        JwtTokenUtil.class,
        JwtWebUtil.class,
        TestEnvironment.class,
        ApplicationSecurityConfig.class
    }
)
@WithMockUser(roles = "ADMIN")
public class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void whenGetUsers_thenReturnUsers() throws Exception {
        User user1ToReturn = new User();
        user1ToReturn.setUsername("user1");
        user1ToReturn.setPassword("password1");
        User user2ToReturn = new User();
        user2ToReturn.setUsername("user2");
        user2ToReturn.setPassword("password2");
        Mockito.when(userService.getAll()).thenReturn(Set.of(user1ToReturn, user2ToReturn));

        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void whenGetUser_thenReturnUser() throws Exception {
        User userToReturn = new User();
        userToReturn.setUsername("username");
        userToReturn.setId(0L);
        Mockito.when(userService.get(0L)).thenReturn(userToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/0")).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenGetNonExistingUser_thenError() throws Exception {
        Mockito.when(userService.get(0L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenDeleteUser_thenDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/0")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenDeleteNonExistingUser_thenError() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(userService).remove(0L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/0"))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenUpdate_thenReturnUpdate() throws Exception {
        User updated = new User();
        updated.setId(0);
        updated.setUsername("username");
        updated.setPassword("password");
        Mockito.when(userService.update(0L, updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/0").content(objectMapper.writeValueAsString(updated))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }


    @Test
    void whenUpdateNonExistingUser_thenError() throws Exception {
        User updated = new User();
        updated.setId(0);
        updated.setUsername("username");
        updated.setPassword("password");
        Mockito.when(userService.update(0L, updated)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/0").content(objectMapper.writeValueAsString(updated))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void whenCreateUser_thenReturnUser() throws Exception {
        User toCreate = new User();
        toCreate.setId(0);
        toCreate.setUsername("username");
        toCreate.setPassword("password");
        Mockito.when(userService.save(toCreate)).thenReturn(toCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(objectMapper.writeValueAsString(toCreate))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));
    }

}
