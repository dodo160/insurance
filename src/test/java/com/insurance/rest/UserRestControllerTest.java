package com.insurance.rest;

import com.insurance.TestUtils;
import com.insurance.enums.UserType;
import com.insurance.json.JsonMarshaller;
import com.insurance.mapper.UserMapper;
import com.insurance.model.User;
import com.insurance.modeldto.UserDTO;
import com.insurance.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Test
    public void getByIdTest() throws Exception {
        when(userService.findById(1l)).thenReturn(TestUtils.buildUser(UserType.CLIENT));

        mockMvc.perform(get("/app/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"}"));

    }

    @Test
    public void getAllUsersTest() throws Exception {
        when(userService.findAll()).thenReturn(Set.of(TestUtils.buildUser(UserType.CLIENT)));

        mockMvc.perform(get("/app/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"}]"));
    }

    @Test
    public void addUserClientTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.CLIENT);
        final UserDTO userDTO = userMapper.userToUserDTO(user);

        when(userService.add(user)).thenReturn(user);

        mockMvc.perform(post("/app/user/add")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMarshaller.toJson(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addUserEmployeeTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.EMPLOYEE);
        final UserDTO userDTO = userMapper.userToUserDTO(user);

        when(userService.add(user)).thenReturn(user);

        mockMvc.perform(post("/app/user/add")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMarshaller.toJson(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/app/user/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void softDeleteUserTest() throws Exception {
        mockMvc.perform(delete("/app/user/softDelete/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
