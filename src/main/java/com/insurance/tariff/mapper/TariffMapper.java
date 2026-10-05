package com.insurance.tariff.mapper;

import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import org.mapstruct.Mapper;

@Mapper
public interface TariffMapper {

    TariffDTO toDto(Tariff entity);

    Tariff fromDto(TariffDTO dto);
}
