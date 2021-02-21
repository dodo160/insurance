package com.insurance.service;

import com.insurance.model.TemporalEntity;

import javax.xml.bind.ValidationException;


public interface TemporalEntityService extends BasicService<TemporalEntity,Long>{

    void createEntityFromTemporal(Long id) throws ValidationException;
}
