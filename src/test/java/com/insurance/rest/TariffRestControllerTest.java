package com.insurance.rest;

import com.insurance.TestUtils;
import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import com.insurance.json.JsonMarshaller;
import com.insurance.mapper.TariffMapper;
import com.insurance.model.Tariff;
import com.insurance.modeldto.TariffDTO;
import com.insurance.service.TariffService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TariffRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TariffService tariffService;

    @Autowired
    private TariffMapper tariffMapper;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Test
    public void getByIdTest() throws Exception {
        when(tariffService.findById(1l)).thenReturn(TestUtils.buildTariff(InsuranceType.YEAR));

        mockMvc.perform(get("/app/tariff/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.insuranceType").value(InsuranceType.YEAR.name()))
                .andExpect(jsonPath("$.packet").value(Packet.BASIC.name()))
                .andExpect(jsonPath("$.price").value("39.0"));

    }

    @Test
    public void getAllTariffsTest() throws Exception {
        when(tariffService.findAll()).thenReturn(Set.of(TestUtils.buildTariff(InsuranceType.YEAR)));

        mockMvc.perform(get("/app/tariffs")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00}]"));
    }

    @Test
    public void addTariffTest() throws Exception {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);
        final TariffDTO tariffDTO = tariffMapper.tariffToTariffDTO(tariff);

        when(tariffService.add(tariff)).thenReturn(tariff);

        mockMvc.perform(post("/app/tariff/add")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMarshaller.toJson(tariffDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTariffTest() throws Exception {
        mockMvc.perform(delete("/app/tariff/delete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void softDeleteTariffTest() throws Exception {
        mockMvc.perform(delete("/app/tariff/softDelete/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getTariffByInsuranceTypeAndPacketTest() throws Exception {
        when(tariffService.getTariffInsuranceTypeAndPacket(InsuranceType.YEAR, Packet.BASIC)).thenReturn(TestUtils.buildTariff(InsuranceType.YEAR));

        mockMvc.perform(get("/app/tariffByInsuranceTypeAndPacket?insuranceType=YEAR&packet=BASIC")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("insuranceType", InsuranceType.YEAR.name()).param("packet", Packet.BASIC.name()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"insuranceType\":\"YEAR\",\"packet\":\"BASIC\",\"price\":39.00}"));
    }
}
