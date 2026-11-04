package com.insurance.tariff.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.tariff.model.Tariff;
import org.mapstruct.Mapper;

@Mapper
public interface TariffMapper extends CommonMapper<Tariff, TariffDTO> {
}
