package com.gamermetrics.GamerMetrics.userplay;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPlayRequest {

    private String name;

    private String description;

    @NotBlank(message = "Map name is required, provide one of the following: " +
            "DE_OVERPASS, DE_ANUBIS, DE_INFERNO, DE_MIRAGE, DE_VERTIGO, DE_NUKE, DE_ANCIENT")
    @Size(min = 7, max = 11, message = "Map name must be one of the following: " +
            "DE_OVERPASS, DE_ANUBIS, DE_INFERNO, DE_MIRAGE, DE_VERTIGO, DE_NUKE, DE_ANCIENT")
    private String map;

}
