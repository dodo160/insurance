package com.insurance.insurance.mapper;

import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.insurance.model.Insurance;
import com.insurance.insurance.model.Reinsurance;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.user.mapper.UserMapper;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InsuranceMapperTest {

    private InsuranceMapper insuranceMapper;

    @BeforeEach
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

        assertNotNull(toDto);
        assertEquals(Long.valueOf(1l), toDto.getId());
        assertNotNull(toDto.getTariff());
        assertEquals(insurance.getTariff().getId(), toDto.getTariff().getId());
        assertEquals(insurance.getTariff().getInsuranceType(), toDto.getTariff().getInsuranceType());
        assertEquals(insurance.getTariff().getPacket(), toDto.getTariff().getPacket());
        assertEquals(insurance.getTariff().getPrice(), toDto.getTariff().getPrice());
        assertNotNull(toDto.getUser());
        assertEquals(insurance.getUser().getId(), toDto.getUser().getId());
        assertEquals(insurance.getUser().getFirstName(), toDto.getUser().getFirstName());
        assertEquals(insurance.getUser().getLastName(), toDto.getUser().getLastName());
        assertEquals(insurance.getUser().getCity(), toDto.getUser().getCity());
        assertEquals(insurance.getUser().getAddress(), toDto.getUser().getAddress());
        assertEquals(insurance.getUser().getPostCode(), toDto.getUser().getPostCode());
        assertEquals(insurance.getUser().getIdentityId(), toDto.getUser().getIdentityId());
        assertEquals(insurance.getUser().getUserType(), toDto.getUser().getUserType());
        assertNotNull(toDto.getReinsurances());
        assertEquals(insurance.getReinsurances().size(), toDto.getReinsurances().size());
        assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(0).getId(), toDto.getReinsurances().get(0).getId());
        assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(0).getReinsuranceType(), toDto.getReinsurances().get(0).getReinsuranceType());
        assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(1).getId(), toDto.getReinsurances().get(1).getId());
        assertEquals(((List<Reinsurance>) insurance.getReinsurances()).get(1).getReinsuranceType(), toDto.getReinsurances().get(1).getReinsuranceType());
        assertEquals(insurance.getPerson(), toDto.getPerson());
    }

    @Test
    public void fromDtoTest() {
        final InsuranceDTO dto = TestUtils.buildInsuranceDto(InsuranceType.YEAR);

        final Insurance insurance = insuranceMapper.fromDto(dto);

        assertNotNull(insurance);
        assertEquals(Long.valueOf(1l), insurance.getId());
        assertNotNull(insurance.getTariff());
        assertEquals(dto.getTariff().getId(), insurance.getTariff().getId());
        assertEquals(dto.getTariff().getInsuranceType(), insurance.getTariff().getInsuranceType());
        assertEquals(dto.getTariff().getPacket(), insurance.getTariff().getPacket());
        assertEquals(dto.getTariff().getPrice(), insurance.getTariff().getPrice());
        assertNotNull(dto.getUser());
        assertEquals(dto.getUser().getId(), insurance.getUser().getId());
        assertEquals(dto.getUser().getFirstName(), insurance.getUser().getFirstName());
        assertEquals(dto.getUser().getLastName(), insurance.getUser().getLastName());
        assertEquals(dto.getUser().getCity(), insurance.getUser().getCity());
        assertEquals(dto.getUser().getAddress(), insurance.getUser().getAddress());
        assertEquals(dto.getUser().getPostCode(), insurance.getUser().getPostCode());
        assertEquals(dto.getUser().getIdentityId(), insurance.getUser().getIdentityId());
        assertNotNull(dto.getReinsurances());
        assertEquals(dto.getReinsurances().size(), insurance.getReinsurances().size());
        assertEquals(dto.getReinsurances().get(0).getId(), ((List<Reinsurance>) insurance.getReinsurances()).get(0).getId());
        assertEquals(dto.getReinsurances().get(0).getReinsuranceType(), ((List<Reinsurance>) insurance.getReinsurances()).get(0).getReinsuranceType());
        assertEquals(dto.getReinsurances().get(1).getId(), ((List<Reinsurance>) insurance.getReinsurances()).get(1).getId());
        assertEquals(dto.getReinsurances().get(1).getReinsuranceType(), ((List<Reinsurance>) insurance.getReinsurances()).get(1).getReinsuranceType());
        assertEquals(dto.getPerson(), insurance.getPerson());
    }
}
