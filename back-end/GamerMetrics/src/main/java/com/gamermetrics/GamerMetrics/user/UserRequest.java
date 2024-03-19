package com.gamermetrics.GamerMetrics.user;

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
public class UserRequest {

    @NotBlank(message = "Nickname is required")
    @Size(min = 1, max = 20, message = "Nickname must be between 3 and 20 characters")
    private String nickName;

    private String firstName;

    private String lastName;

    private String steamId;

}
