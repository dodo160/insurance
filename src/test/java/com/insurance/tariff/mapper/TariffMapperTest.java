package com.insurance.tariff.mapper;

import com.insurance.TestUtils;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class TariffMapperTest {

    private final TariffMapper tariffMapper =  Mappers.getMapper(TariffMapper.class);

    @Test
    public void toDtoTest() {
        final Tariff tariff = TestUtils.buildTariff(InsuranceType.YEAR);

        final TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        Assert.assertNotNull(tariffDTO);
        Assert.assertEquals(tariff.getId(), tariffDTO.getId());
        Assert.assertEquals(tariff.getInsuranceType(), tariffDTO.getInsuranceType());
        Assert.assertEquals(tariff.getPacket(), tariffDTO.getPacket());
        Assert.assertEquals(tariff.getPrice(), tariffDTO.getPrice());
    }

    @Test
    public void fromDtoTest() {
        final TariffDTO tariffDTO = TestUtils.buildTariffDTO(InsuranceType.YEAR);

        final Tariff tariff = tariffMapper.fromDto(tariffDTO);

        Assert.assertNotNull(tariff);
        Assert.assertEquals(tariffDTO.getId(), tariff.getId());
        Assert.assertEquals(tariffDTO.getInsuranceType(), tariff.getInsuranceType());
        Assert.assertEquals(tariffDTO.getPacket(), tariff.getPacket());
        Assert.assertEquals(tariffDTO.getPrice(), tariff.getPrice());
    }
}
