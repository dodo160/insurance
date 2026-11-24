package com.insurance.temporal.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.mapper.TemporalEntityMapper;
import com.insurance.temporal.mapper.TemporalEntityMapperImpl;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.temporal.service.TemporalEntityService;
import com.insurance.user.mapper.UserMapperImpl;
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

@WebMvcTest(TemporalEntityRestController.class)
@Import({TemporalEntityMapperImpl.class, UserMapperImpl.class})
public class TemporalEntityRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemporalEntityService temporalEntityService;

    @Autowired
    private TemporalEntityMapper temporalEntityMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getByIdTest() throws Exception {
        when(temporalEntityService.findById(1l)).thenReturn(TestUtils.buildTemporalEntity());

        mockMvc.perform(get("/temporalEntity/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"entityClass\":\"Insurance\",\"mediaType\":\"application/xml\",\"entity\":\"XML_STRING\"}"));

    }

    @Test
    public void getAllTemporalEntitiesTest() throws Exception {
        when(temporalEntityService.findAll()).thenReturn(List.of(TestUtils.buildTemporalEntity()));

        mockMvc.perform(get("/temporalEntity")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"entityClass\":\"Insurance\",\"mediaType\":\"application/xml\",\"entity\":\"XML_STRING\"}]"));
    }

    @Test
    public void addTemporalEntityTest() throws Exception {
        final TemporalEntity temporalEntity = TestUtils.buildTemporalEntity();
        final TemporalEntityDTO temporalEntityDTO = temporalEntityMapper.toDto(temporalEntity);

        when(temporalEntityService.add(temporalEntity)).thenReturn(temporalEntity);

        mockMvc.perform(post("/temporalEntity")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(temporalEntityDTO)))
                .andExpect(status().isCreated());
    }


    @Test
    public void deleteTemporalEntityTest() throws Exception {
        mockMvc.perform(delete("/temporalEntity/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createEntityFromTemporalTest() throws Exception {
        mockMvc.perform(post("/temporalEntity/createEntityFromTemporal/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}
