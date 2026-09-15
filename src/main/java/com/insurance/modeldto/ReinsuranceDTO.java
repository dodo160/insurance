package com.insurance.modeldto;

import com.insurance.enums.ReinsuranceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReinsuranceDTO {

    private Long id;
    @NotNull(message = "Missing reinsurance type")
    private ReinsuranceType reinsuranceType;

}
