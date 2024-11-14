package com.essobhi.bookscape.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "firstname is required")
    @NotBlank(message = "firstname is required") // there is no blank spaces
    private String firstname;
    @NotEmpty(message = "lastname is required")
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is required")
    @Email(message = "email is not formatted")
    private String email;
    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
}
