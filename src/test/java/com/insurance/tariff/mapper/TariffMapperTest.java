package com.insurance.tariff.mapper;

import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TariffMapperTest {

    private final TariffMapper tariffMapper =  Mappers.getMapper(TariffMapper.class);

    @Test
    public void toDtoTest() {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);

        final TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        assertNotNull(tariffDTO);
        assertEquals(tariff.getId(), tariffDTO.getId());
        assertEquals(tariff.getInsuranceType(), tariffDTO.getInsuranceType());
        assertEquals(tariff.getPacket(), tariffDTO.getPacket());
        assertEquals(tariff.getPrice(), tariffDTO.getPrice());
    }

    @Test
    public void fromDtoTest() {
        final TariffDTO tariffDTO = TestUtils.buildTariffDTO(InsuranceType.YEAR);

        final Tariff tariff = tariffMapper.fromDto(tariffDTO);

        assertNotNull(tariff);
        assertEquals(tariffDTO.getId(), tariff.getId());
        assertEquals(tariffDTO.getInsuranceType(), tariff.getInsuranceType());
        assertEquals(tariffDTO.getPacket(), tariff.getPacket());
        assertEquals(tariffDTO.getPrice(), tariff.getPrice());
    }
}
