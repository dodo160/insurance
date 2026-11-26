package com.insurance.tariff.mapper;

import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.TariffEmbedded;
import org.mapstruct.Mapper;

@Mapper
public interface TariffEmbeddedMapper {

    TariffDTO toDto(TariffEmbedded tariff);

    TariffEmbedded fromDto(TariffDTO dto);
}
