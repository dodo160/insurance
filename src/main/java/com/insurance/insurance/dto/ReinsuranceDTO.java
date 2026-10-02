package com.insurance.insurance.dto;

import com.insurance.insurance.enums.ReinsuranceType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReinsuranceDTO {

    private Long id;
    @NotNull(message = "Missing reinsurance type")
    private ReinsuranceType reinsuranceType;

}
