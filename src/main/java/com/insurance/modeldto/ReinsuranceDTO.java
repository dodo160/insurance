package com.insurance.modeldto;

import com.insurance.enums.ReinsuranceType;

public class ReinsuranceDTO {

    private Long id;
    private InsuranceDTO insurance;
    private ReinsuranceType reinsuranceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsuranceDTO getInsurance() {
        return insurance;
    }

    public void setInsurance(InsuranceDTO insurance) {
        this.insurance = insurance;
    }

    public ReinsuranceType getReinsuranceType() {
        return reinsuranceType;
    }

    public void setReinsuranceType(ReinsuranceType reinsuranceType) {
        this.reinsuranceType = reinsuranceType;
    }
}
