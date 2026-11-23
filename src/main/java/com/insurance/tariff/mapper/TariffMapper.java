package com.insurance.tariff.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TariffMapper extends CommonMapper<Tariff, TariffDTO> {

    @Override
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    Tariff fromDto(TariffDTO dto);
}
