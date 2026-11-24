package com.insurance.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.insurance.mapper.InsuranceMapperImpl;
import com.insurance.insurance.mapper.ReinsuranceMapperImpl;
import com.insurance.tariff.mapper.TariffMapperImpl;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.mapper.ClientMapperImpl;
import com.insurance.user.mapper.EmployeeMapperImpl;
import com.insurance.user.mapper.UserMapper;
import com.insurance.user.mapper.UserMapperImpl;
import com.insurance.user.model.User;
import com.insurance.user.service.UserService;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@Import({InsuranceMapperImpl.class, UserMapperImpl.class, ClientMapperImpl.class, EmployeeMapperImpl.class,
        TariffMapperImpl.class, ReinsuranceMapperImpl.class})
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getByIdTest() throws Exception {
        when(userService.findById(1l)).thenReturn(TestUtils.buildUser(UserType.CLIENT));

        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"}"));

    }

    @Test
    public void getAllUsersTest() throws Exception {
        when(userService.findAll()).thenReturn(List.of(TestUtils.buildUser(UserType.CLIENT)));

        mockMvc.perform(get("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"}]"));
    }

    @Test
    public void addUserClientTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.CLIENT);
        final UserDTO userDTO = userMapper.toDto(user);

        when(userService.add(user)).thenReturn(user);

        mockMvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUserClientTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.CLIENT);
        final UserDTO userDTO = userMapper.toDto(user);

        when(userService.update(user.getId(), user)).thenReturn(user);

        mockMvc.perform(put("/user/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserEmployeeTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.EMPLOYEE);
        final UserDTO userDTO = userMapper.toDto(user);

        when(userService.add(user)).thenReturn(user);

        mockMvc.perform(post("/user")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUserEmployeeTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.EMPLOYEE);
        final UserDTO userDTO = userMapper.toDto(user);

        when(userService.update(user.getId(), user)).thenReturn(user);

        mockMvc.perform(put("/user/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserEmployeeRequestMismatchExceptionTest() throws Exception {
        final User user = TestUtils.buildUser(UserType.EMPLOYEE);
        final UserDTO userDTO = userMapper.toDto(user);

        when(userService.update(user.getId(), user)).thenReturn(user);

        mockMvc.perform(put("/user/3")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void softDeleteUserTest() throws Exception {
        mockMvc.perform(put("/user/softDelete/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
