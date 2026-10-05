package com.insurance.insurance.mapper;

import com.insurance.TestUtils;
import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.model.Reinsurance;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.user.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class InsuranceMapperTest {

    private InsuranceMapper insuranceMapper;

    @Before
    public void setUp() {
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        ReinsuranceMapper reinsuranceMapper = Mappers.getMapper(ReinsuranceMapper.class);
        TariffMapper tariffMapper = Mappers.getMapper(TariffMapper.class);

        insuranceMapper = new InsuranceMapperImpl(tariffMapper, userMapper, reinsuranceMapper);
    }

    @Test
    public void toDtoTest() {
        final Insurance insurance = TestUtils.buildInsurance(InsuranceType.YEAR);

        final InsuranceDTO toDto = insuranceMapper.toDto(insurance);

        Assert.assertNotNull(toDto);
        Assert.assertEquals(Long.valueOf(1l), toDto.getId());
        Assert.assertNotNull(toDto.getTariff());
        Assert.assertEquals(insurance.getTariff().getId(), toDto.getTariff().getId());
        Assert.assertEquals(insurance.getTariff().getInsuranceType(), toDto.getTariff().getInsuranceType());
        Assert.assertEquals(insurance.getTariff().getPacket(), toDto.getTariff().getPacket());
        Assert.assertEquals(insurance.getTariff().getPrice(), toDto.getTariff().getPrice());
        Assert.assertNotNull(toDto.getUser());
        Assert.assertEquals(insurance.getUser().getId(), toDto.getUser().getId());
        Assert.assertEquals(insurance.getUser().getFirstName(), toDto.getUser().getFirstName());
        Assert.assertEquals(insurance.getUser().getLastName(), toDto.getUser().getLastName());
        Assert.assertEquals(insurance.getUser().getCity(), toDto.getUser().getCity());
        Assert.assertEquals(insurance.getUser().getAddress(), toDto.getUser().getAddress());
        Assert.assertEquals(insurance.getUser().getPostCode(), toDto.getUser().getPostCode());
        Assert.assertEquals(insurance.getUser().getIdentityId(), toDto.getUser().getIdentityId());
        Assert.assertEquals(insurance.getUser().getUserType(), toDto.getUser().getUserType());
        Assert.assertNotNull(toDto.getReinsurances());
        Assert.assertEquals(insurance.getReinsurances().size(), toDto.getReinsurances().size());
        Assert.assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(0).getId(), toDto.getReinsurances().get(0).getId());
        Assert.assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(0).getReinsuranceType(), toDto.getReinsurances().get(0).getReinsuranceType());
        Assert.assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(1).getId(), toDto.getReinsurances().get(1).getId());
        Assert.assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(1).getReinsuranceType(), toDto.getReinsurances().get(1).getReinsuranceType());
        Assert.assertEquals(insurance.getPerson(), toDto.getPerson());
    }

    @Test
    public void fromDtoTest() {
        final InsuranceDTO dto = TestUtils.buildInsuranceDto(InsuranceType.YEAR);

        final Insurance insurance = insuranceMapper.fromDto(dto);

        Assert.assertNotNull(insurance);
        Assert.assertEquals(Long.valueOf(1l), insurance.getId());
        Assert.assertNotNull(insurance.getTariff());
        Assert.assertEquals(dto.getTariff().getId(), insurance.getTariff().getId());
        Assert.assertEquals(dto.getTariff().getInsuranceType(), insurance.getTariff().getInsuranceType());
        Assert.assertEquals(dto.getTariff().getPacket(), insurance.getTariff().getPacket());
        Assert.assertEquals(dto.getTariff().getPrice(), insurance.getTariff().getPrice());
        Assert.assertNotNull(dto.getUser());
        Assert.assertEquals(dto.getUser().getId(), insurance.getUser().getId());
        Assert.assertEquals(dto.getUser().getFirstName(), insurance.getUser().getFirstName());
        Assert.assertEquals(dto.getUser().getLastName(), insurance.getUser().getLastName());
        Assert.assertEquals(dto.getUser().getCity(), insurance.getUser().getCity());
        Assert.assertEquals(dto.getUser().getAddress(), insurance.getUser().getAddress());
        Assert.assertEquals(dto.getUser().getPostCode(), insurance.getUser().getPostCode());
        Assert.assertEquals(dto.getUser().getIdentityId(), insurance.getUser().getIdentityId());
        Assert.assertNotNull(dto.getReinsurances());
        Assert.assertEquals(dto.getReinsurances().size(), insurance.getReinsurances().size());
        Assert.assertEquals(dto.getReinsurances().get(0).getId(), ((List<Reinsurance>) insurance.getReinsurances()).get(0).getId());
        Assert.assertEquals(dto.getReinsurances().get(0).getReinsuranceType(), ((List<Reinsurance>) insurance.getReinsurances()).get(0).getReinsuranceType());
        Assert.assertEquals(dto.getReinsurances().get(1).getId(), ((List<Reinsurance>) insurance.getReinsurances()).get(1).getId());
        Assert.assertEquals(dto.getReinsurances().get(1).getReinsuranceType(), ((List<Reinsurance>) insurance.getReinsurances()).get(1).getReinsuranceType());
        Assert.assertEquals(dto.getPerson(), insurance.getPerson());
    }
}
