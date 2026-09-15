package com.insurance.modeldto;

import com.insurance.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
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
    @NotBlank(message = "Missing user type")
    private UserType userType;
}
