package com.insurance.rest;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.json.JsonUtils;
import com.insurance.mapper.InsuranceMapper;
import com.insurance.model.Insurance;
import com.insurance.modeldto.InsuranceDTO;
import com.insurance.service.InsuranceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InsuranceRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceService insuranceService;

    @Autowired
    private InsuranceMapper insuranceMapper;

    @Test
    public void getInsuranceByIdTest() throws Exception {
        when(insuranceService.findById(1l)).thenReturn(TestUtils.buildInsurance(InsuranceType.YEAR));

        mockMvc.perform(get("/app/insurance/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tariff.insuranceType").value("YEAR"))
                .andExpect(jsonPath("$.reinsurances").isArray())
                .andExpect(jsonPath("$.reinsurances[*].reinsuranceType").isNotEmpty())
                .andExpect(content().json("{\"id\":1,\"tariff\":{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00},\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"startDate\":\"2024-01-29\",\"endDate\":\"2025-01-29\",\"reinsurances\":[{\"id\":1,\"reinsuranceType\":\"STORNO\"},{\"id\":2,\"reinsuranceType\":\"SPORTS_ACTIVITY\"}],\"person\":2,\"price\":null}"));

    }

    @Test
    public void getAllInsurancesTest() throws Exception {
        when(insuranceService.findAll()).thenReturn(Set.of(TestUtils.buildInsurance(InsuranceType.YEAR)));

        mockMvc.perform(get("/app/insurances").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"tariff\":{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00},\"user\":{\"id\":1,\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"city\":\"CITY\",\"address\":\"ADDRESS\",\"postCode\":\"1111\",\"identityId\":\"2222\",\"userType\":\"CLIENT\"},\"startDate\":\"2024-01-29\",\"endDate\":\"2025-01-29\",\"reinsurances\":[{\"id\":1,\"reinsuranceType\":\"STORNO\"},{\"id\":2,\"reinsuranceType\":\"SPORTS_ACTIVITY\"}],\"person\":2,\"price\":null}]"));
    }

    @Test
    public void addInsuracnceTest() throws Exception {
        final InsuranceDTO insuranceDTO = insuranceMapper.insuranceToInsuranceDTO(TestUtils.buildInsurance(InsuranceType.YEAR));

        mockMvc.perform(post("/app/insurance/add")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(insuranceDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateInsuranceTest() throws Exception {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        final InsuranceDTO insuranceDTO = insuranceMapper.insuranceToInsuranceDTO(insurance);

        when(insuranceService.update(insurance)).thenReturn(insurance);

        mockMvc.perform(put("/app/insurance/update")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(insuranceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void deleteInsuracnceTest() throws Exception {
        mockMvc.perform(delete("/app/insurance/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void softDeleteInsuracnceTest() throws Exception {
        mockMvc.perform(delete("/app/insurance/softDelete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void calculateInsuranceTest() throws Exception {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);
        final InsuranceDTO insuranceDTO = insuranceMapper.insuranceToInsuranceDTO(insurance);

        when(insuranceService.calculateInsurance(insurance)).thenReturn(BigDecimal.TEN);

        mockMvc.perform(get("/app/insurance/calculate")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.toJson(insuranceDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(BigDecimal.TEN.toString()));
    }
}
