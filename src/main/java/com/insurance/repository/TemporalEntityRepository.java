package com.insurance.repository;

import com.insurance.model.TemporalEntity;
import org.springframework.data.repository.CrudRepository;

public interface TemporalEntityRepository extends CrudRepository<TemporalEntity, Long> {
}
