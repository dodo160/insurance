package com.insurance.modeldto;

import com.insurance.enums.InsuranceType;
import com.insurance.enums.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TariffDTO {

    private Long id;
    @NotNull(message = "Missing insurance type")
    private InsuranceType insuranceType;
    @NotNull(message = "Missing packet")
    private Packet packet;
    private BigDecimal price;
    private boolean active;

}
