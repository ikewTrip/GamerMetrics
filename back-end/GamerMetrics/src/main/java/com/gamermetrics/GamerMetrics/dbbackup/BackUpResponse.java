package com.gamermetrics.GamerMetrics.dbbackup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackUpResponse {
    private String fileName;
}
