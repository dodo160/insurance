package com.insurance.service;

import com.insurance.model.Insurance;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;

public interface InsuranceService extends BasicService<Insurance,Long>{

	BigDecimal calculateInsurance(Insurance insurance) throws ValidationException;
}
