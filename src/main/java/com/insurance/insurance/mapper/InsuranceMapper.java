package com.insurance.insurance.mapper;

import com.insurance.insurance.dto.InsuranceDTO;
import com.insurance.insurance.model.Insurance;
import com.insurance.tariff.mapper.TariffMapper;
import com.insurance.user.mapper.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {TariffMapper.class, UserMapper.class, ReinsuranceMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InsuranceMapper {

    InsuranceDTO toDto(Insurance entity);

    Insurance fromDto(InsuranceDTO dto);
}
