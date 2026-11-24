package com.insurance.temporal.repository;

import com.insurance.temporal.model.TemporalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporalEntityRepository extends CrudRepository<TemporalEntity, Long> {
}
