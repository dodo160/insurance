package com.insurance.service;

import com.insurance.model.TemporalEntity;

import javax.validation.constraints.NotNull;


public interface TemporalEntityService extends BasicService<TemporalEntity, Long> {

    void createEntityFromTemporal(@NotNull Long id);
}
