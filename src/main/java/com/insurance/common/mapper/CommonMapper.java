package com.insurance.common.mapper;

import com.insurance.common.dto.Dto;
import com.insurance.common.model.BaseEntity;

import java.util.List;

public interface CommonMapper <MODEL extends BaseEntity, DTO extends Dto> {

    DTO toDto(MODEL entity);

    MODEL fromDto(DTO dto);

    List<DTO> toDtoList(List<MODEL> list);

    List<MODEL> fromDtoList(List<DTO> list);
}
