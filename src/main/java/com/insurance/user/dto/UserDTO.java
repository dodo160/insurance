package com.insurance.user.dto;

import com.insurance.user.enums.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "Missing first name")
    private String firstName;
    @NotBlank(message = "Missing last name")
    private String lastName;
    @NotBlank(message = "Missing city")
    private String city;
    @NotBlank(message = "Missing address")
    private String address;
    @NotBlank(message = "Missing post code")
    private String postCode;
    @NotBlank(message = "Missing identity id")
    private String identityId;
    @NotNull(message = "Missing packet")
    private UserType userType;

}
