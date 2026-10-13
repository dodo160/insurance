package com.insurance.tariff.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.enums.Packet;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.tariff.mapper.TariffMapperImpl;
import com.insurance.tariff.model.Tariff;
import com.insurance.tariff.service.TariffService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TariffRestController.class)
@Import(TariffMapperImpl.class)
public class TariffRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TariffService tariffService;

    @Autowired
    private TariffMapper tariffMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getByIdTest() throws Exception {
        when(tariffService.findById(1l)).thenReturn(TestUtils.buildTariff(InsuranceType.YEAR));

        mockMvc.perform(get("/tariff/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.insuranceType").value(InsuranceType.YEAR.name()))
                .andExpect(jsonPath("$.packet").value(Packet.BASIC.name()))
                .andExpect(jsonPath("$.price").value("39.0"));

    }

    @Test
    public void getAllTariffsTest() throws Exception {
        when(tariffService.findAll()).thenReturn(List.of(TestUtils.buildTariff(InsuranceType.YEAR)));

        mockMvc.perform(get("/tariff")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00}]"));
    }

    @Test
    public void addTariffTest() throws Exception {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        when(tariffService.add(tariff)).thenReturn(tariff);

        mockMvc.perform(post("/tariff")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(tariffDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTariffTest() throws Exception {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        when(tariffService.update(tariff.getId(), tariff)).thenReturn(tariff);

        mockMvc.perform(put("/tariff/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(tariffDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTariffRequestMismatchExceptionTest() throws Exception {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        when(tariffService.update(tariff.getId(), tariff)).thenReturn(tariff);

        mockMvc.perform(put("/tariff/2")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(tariffDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTariffTest() throws Exception {
        mockMvc.perform(delete("/tariff/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getTariffByInsuranceTypeAndPacketTest() throws Exception {
        when(tariffService.getTariffByInsuranceTypeAndPacketAndActiveTrue(InsuranceType.YEAR, Packet.BASIC)).thenReturn(TestUtils.buildTariff(InsuranceType.YEAR));

        mockMvc.perform(get("/tariff/tariffByInsuranceTypeAndPacket?insuranceType=YEAR&packet=BASIC")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("insuranceType", InsuranceType.YEAR.name()).param("packet", Packet.BASIC.name()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00}"));
    }
}
