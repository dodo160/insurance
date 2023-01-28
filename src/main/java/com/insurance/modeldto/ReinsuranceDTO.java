package com.insurance.modeldto;

import com.insurance.enums.ReinsuranceType;

public class ReinsuranceDTO {

    private Long id;
    private ReinsuranceType reinsuranceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReinsuranceType getReinsuranceType() {
        return reinsuranceType;
    }

    public void setReinsuranceType(ReinsuranceType reinsuranceType) {
        this.reinsuranceType = reinsuranceType;
    }
}
