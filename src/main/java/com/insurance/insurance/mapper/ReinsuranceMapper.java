package com.insurance.insurance.mapper;

import com.insurance.insurance.dto.ReinsuranceDTO;
import com.insurance.insurance.model.Reinsurance;
import org.mapstruct.Mapper;

@Mapper
public interface ReinsuranceMapper {

    ReinsuranceDTO toDto(Reinsurance entity);

    Reinsurance fromDto(ReinsuranceDTO dto);
}
