package com.insurance.mapper;

import com.insurance.model.Reinsurance;
import com.insurance.modeldto.ReinsuranceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReinsuranceMapper {

    ReinsuranceDTO toDto(Reinsurance entity);

    Reinsurance fromDto(ReinsuranceDTO dto);
}
