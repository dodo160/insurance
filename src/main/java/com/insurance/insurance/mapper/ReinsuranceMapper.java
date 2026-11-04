package com.insurance.insurance.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.insurance.dto.ReinsuranceDTO;
import com.insurance.insurance.model.Reinsurance;
import org.mapstruct.Mapper;

@Mapper
public interface ReinsuranceMapper extends CommonMapper<Reinsurance, ReinsuranceDTO> {
}
