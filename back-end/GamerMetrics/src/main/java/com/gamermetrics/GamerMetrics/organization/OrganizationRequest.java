package com.gamermetrics.GamerMetrics.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OrganizationRequest {

    @NotBlank(message = "Organization name must not be blank")
    @Size(min = 3, max = 20, message = "Organization name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Organization description must not be blank")
    @Size(min = 3, max = 200, message = "Organization description must be between 3 and 200 characters")
    private String description;

}
