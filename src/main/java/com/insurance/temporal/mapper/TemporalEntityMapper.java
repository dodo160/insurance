package com.insurance.temporal.mapper;

import com.insurance.common.mapper.CommonMapper;
import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.user.mapper.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TemporalEntityMapper extends CommonMapper<TemporalEntity, TemporalEntityDTO> {
}
