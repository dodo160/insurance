package com.insurance.tariff.dto;

import com.insurance.common.dto.Dto;
import com.insurance.insurance.enums.InsuranceType;
import com.insurance.tariff.enums.Packet;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TariffDTO implements Dto {

    private Long id;
    @NotNull(message = "Missing insurance type")
    private InsuranceType insuranceType;
    @NotNull(message = "Missing packet")
    private Packet packet;
    private BigDecimal price;
    private boolean active;

}
