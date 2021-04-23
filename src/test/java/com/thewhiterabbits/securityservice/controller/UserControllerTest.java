package com.thewhiterabbits.securityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewhiterabbits.securityservice.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<UserDTO> users = Arrays.asList(mapper.readValue(Paths.get("src/test/resources/contract/user/POST_CREATE_USER.json").toAbsolutePath().toFile(), UserDTO[].class));
        mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(users.get(0))))
                .andDo(print())
                .andExpect(jsonPath("username", is(users.get(0).getUsername())))
                .andExpect(jsonPath("roles[0].roleName", is("USER")))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetUsers() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<UserDTO> users = Arrays.asList(mapper.readValue(Paths.get("src/test/resources/contract/user/GET_USERS.json").toAbsolutePath().toFile(), UserDTO[].class));
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1].username", is(users.get(0).getUsername())))
                .andExpect(jsonPath("$[-1].roles[0].roleName", is(users.get(0).getRoles().get(0).getRoleName())));
    }

    @Test
    public void testLogin() throws Exception{
        Map<String,HashMap> result = new ObjectMapper().readValue(Paths.get("src/test/resources/contract/user/POST_LOGIN_USER.json").toAbsolutePath().toFile(), HashMap.class);
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(result.get("user1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username",is(result.get("user1").get("username"))))
                .andExpect(jsonPath("$.Roles[0]",is("ADMIN")));
    }

    //to mappe object to create String in json format, mapped by attribute name (username attribute = username json propertie)
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}