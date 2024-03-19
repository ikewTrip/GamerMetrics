package com.gamermetrics.GamerMetrics.invite;

import com.gamermetrics.GamerMetrics.metrics.MetricsDetailedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/invites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class InviteController {

    private final InviteService inviteService;

    @GetMapping
    public ResponseEntity<InviteResponse[]> getMetricsByUserPlayId(Principal principal) {
        return ResponseEntity.ok(inviteService.getActiveInvites(principal.getName()));
    }

}
