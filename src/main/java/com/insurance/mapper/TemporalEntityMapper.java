package com.insurance.mapper;

import com.insurance.model.TemporalEntity;
import com.insurance.modeldto.TemporalEntityDTO;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface TemporalEntityMapper {

    TemporalEntityDTO toDto(TemporalEntity entity);

    TemporalEntity fromDto(TemporalEntityDTO dto);
}
