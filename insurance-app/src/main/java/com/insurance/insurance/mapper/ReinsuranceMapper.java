package com.insurance.insurance.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.dto.ReinsuranceDTO;
import com.insurance.insurance.model.Reinsurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReinsuranceMapper extends CommonMapper<Reinsurance, ReinsuranceDTO> {

    @Override
    @Mapping(target = "insurance", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    Reinsurance fromDto(ReinsuranceDTO dto);
}
