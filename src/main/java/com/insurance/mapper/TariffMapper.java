package com.insurance.mapper;

import com.insurance.model.Tariff;
import com.insurance.modeldto.TariffDTO;
import org.mapstruct.Mapper;

@Mapper(uses = InsuranceMapper.class)
public interface TariffMapper {

    TariffDTO toDto(Tariff entity);

    Tariff fromDto(TariffDTO dto);
}
