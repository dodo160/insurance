package com.insurance.repository;

import com.insurance.model.TemporalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporalEntityRepository extends CrudRepository<TemporalEntity, Long> {
}
