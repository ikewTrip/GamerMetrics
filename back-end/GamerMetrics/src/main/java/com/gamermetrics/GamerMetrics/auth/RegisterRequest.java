package com.gamermetrics.GamerMetrics.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Nickname is required")
    @Size(min = 1, max = 20, message = "Nickname must be between 3 and 20 characters")
    private String nickName;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter an email")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
