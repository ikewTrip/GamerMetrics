package com.gamermetrics.GamerMetrics.result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/results")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResultController {

    private final ResultService resultService;

    @PutMapping("{userPlayId}")
    public ResponseEntity<ResultResponse> updateResult(@RequestBody @Valid ResultRequest resultRequest,
                                                       @PathVariable Integer userPlayId, Principal principal) {
        return new ResponseEntity<>(resultService.updateResult(resultRequest, userPlayId, principal), HttpStatus.OK);
    }

    @GetMapping("{userPlayId}")
    public ResponseEntity<ResultResponse> getResult(@PathVariable Integer userPlayId, Principal principal) {
        return new ResponseEntity<>(resultService.getResult(userPlayId, principal), HttpStatus.OK);
    }

}
