package com.gamermetrics.GamerMetrics.metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("{userPlayId}")
    public ResponseEntity<MetricsDetailedResponse> getMetricsByUserPlayId(@PathVariable Integer userPlayId, Principal principal) {
        return ResponseEntity.ok(metricsService.getMetricsByUserPlayId(userPlayId, principal.getName()));
    }

}
