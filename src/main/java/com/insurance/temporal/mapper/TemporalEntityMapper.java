package com.insurance.temporal.mapper;

import com.insurance.temporal.dto.TemporalEntityDTO;
import com.insurance.temporal.model.TemporalEntity;
import com.insurance.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface TemporalEntityMapper {

    TemporalEntityDTO toDto(TemporalEntity entity);

    TemporalEntity fromDto(TemporalEntityDTO dto);
}
