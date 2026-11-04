package com.insurance.insurance.dto;

import com.insurance.common.dto.Dto;
import com.insurance.insurance.enums.ReinsuranceType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReinsuranceDTO implements Dto {

    private Long id;
    @NotNull(message = "Missing reinsurance type")
    private ReinsuranceType reinsuranceType;

}
