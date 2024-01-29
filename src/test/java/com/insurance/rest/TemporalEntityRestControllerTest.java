package com.insurance.rest;

import com.insurance.TestUtils;
import com.insurance.json.JsonUtils;
import com.insurance.mapper.TemporalEntityMapper;
import com.insurance.model.TemporalEntity;
import com.insurance.modeldto.TemporalEntityDTO;
import com.insurance.service.TemporalEntityService;
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
public class TemporalEntityRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemporalEntityService temporalEntityService;

    @Autowired
    private TemporalEntityMapper temporalEntityMapper;

    @Test
    public void getByIdTest() throws Exception {
        when(temporalEntityService.findById(1l)).thenReturn(TestUtils.buildTemporalEntity());

        mockMvc.perform(get("/app/temporalEntity/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"entityClass\":\"Insurance\",\"mediaType\":\"application/xml\",\"entity\":\"XML_STRING\"}"));

    }

    @Test
    public void getAllTemporalEntitiesTest() throws Exception {
        when(temporalEntityService.findAll()).thenReturn(Set.of(TestUtils.buildTemporalEntity()));

        mockMvc.perform(get("/app/temporalEntities")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"entityClass\":\"Insurance\",\"mediaType\":\"application/xml\",\"entity\":\"XML_STRING\"}]"));
    }

    @Test
    public void addTemporalEntityTest() throws Exception {
        final TemporalEntity temporalEntity = TestUtils.buildTemporalEntity();
        final TemporalEntityDTO temporalEntityDTO = temporalEntityMapper.temporalEntityToTemporalEntityDTO(temporalEntity);

        when(temporalEntityService.add(temporalEntity)).thenReturn(temporalEntity);

        mockMvc.perform(post("/app/temporalEntity/add")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(temporalEntityDTO)))
                .andExpect(status().isCreated());
    }


    @Test
    public void deleteTemporalEntityTest() throws Exception {
        mockMvc.perform(delete("/app/temporalEntity/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createEntityFromTemporalTest() throws Exception {
        mockMvc.perform(put("/app/temporalEntity/{id}/createEntityFromTemporal", 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}
