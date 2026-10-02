package com.insurance.insurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.insurance.tariff.dto.TariffDTO;
import com.insurance.user.dto.UserDTO;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceDTO {

    private Long id;
    @NotNull(message = "Missing tariff")
    private TariffDTO tariff;
    @NotNull(message = "Missing user")
    private UserDTO user;
    @NotNull(message = "Missing strart date")
    @FutureOrPresent(message = "Invalid startDate is before today")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    @NotNull(message = "Missing end date")
    @Future(message = "Invalid endDate is before today or today")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;
    @Valid
    @JsonIgnoreProperties("insurance")
    private List<ReinsuranceDTO> reinsurances = new ArrayList<>();
    @Range(min = 1, max = 3, message = "Person must be between 1 and 3")
    private int person;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

}