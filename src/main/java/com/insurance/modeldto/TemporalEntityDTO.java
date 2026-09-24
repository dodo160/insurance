package com.insurance.modeldto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemporalEntityDTO {

    private Long id;
    @Valid
    @NotNull(message = "Missing user")
    private UserDTO user;
    @NotBlank(message = "Missing entity class")
    private String entityClass;
    @NotBlank(message = "Missing media type")
    private String mediaType;
    @NotBlank(message = "Missing entity")
    private String entity;
}
