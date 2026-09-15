package com.insurance.service;

import com.insurance.model.Insurance;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface InsuranceService extends BasicService<Insurance, Long> {

    BigDecimal calculateInsurance(@Valid @NotNull(message = "Insurance must not be null") Insurance insurance);
}
