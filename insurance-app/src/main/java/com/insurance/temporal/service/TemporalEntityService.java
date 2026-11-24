package com.insurance.temporal.service;

import com.insurance.common.service.BasicService;
import com.insurance.temporal.model.TemporalEntity;

import javax.validation.constraints.NotNull;


public interface TemporalEntityService extends BasicService<TemporalEntity, Long> {

    void createEntityFromTemporal(@NotNull Long id);
}
