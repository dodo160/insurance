package com.insurance.repository;

import com.insurance.model.Insurance;
import org.springframework.data.repository.CrudRepository;

public interface InsuranceRepository extends CrudRepository<Insurance,Long> {

}
