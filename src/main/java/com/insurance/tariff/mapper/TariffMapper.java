package com.insurance.tariff.mapper;

import com.insurance.insurance.mapper.InsuranceMapper;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface TariffMapper {

    TariffDTO toDto(Tariff entity);

    Tariff fromDto(TariffDTO dto);
}
