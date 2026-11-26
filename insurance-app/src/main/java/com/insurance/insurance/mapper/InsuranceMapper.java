package com.insurance.insurance.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.model.Insurance;
import com.insurance.tariff.mapper.TariffEmbeddedMapper;
import com.insurance.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TariffEmbeddedMapper.class, UserMapper.class, ReinsuranceMapper.class})
public interface InsuranceMapper extends CommonMapper<Insurance, InsuranceDTO> {

    @Override
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    Insurance fromDto(InsuranceDTO dto);
}
