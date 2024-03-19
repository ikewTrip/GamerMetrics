package com.gamermetrics.GamerMetrics.userplay;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/userplays")
@RequiredArgsConstructor
public class UserPlayController {

    private final UserPlayService userPlayService;

    @GetMapping("/my/{userPlayId}")
    public ResponseEntity<UserPlayResponse> getUserPlayById(@PathVariable Integer userPlayId, Principal principal) {
        return new ResponseEntity<>(userPlayService.getUserPlayById(userPlayId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<UserPlayResponse[]> getUserPlaysByNickName(@PathVariable String nickName) {
        return new ResponseEntity<>(userPlayService.getUserPlaysByNickName(nickName), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<UserPlayResponse[]> getAllUserPlays(Principal principal) {
        return new ResponseEntity<>(userPlayService.getAllUserPlays(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/state")
    public ResponseEntity<Boolean> getIfUserPlayAlreadyStarted(Principal principal) {
        return new ResponseEntity<>(userPlayService.getIfUserPlayAlreadyStarted(principal.getName()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserPlayResponse> startSoloPlay(Principal principal,
                                                          @RequestBody @Valid UserPlayRequest userPlayRequest) {
        return new ResponseEntity<>(userPlayService.createSoloPlay(userPlayRequest, principal), HttpStatus.CREATED);
    }

    @PostMapping("/stop")
    public ResponseEntity<UserPlayResponse> stopPlay(Principal principal) {
        return new ResponseEntity<>(userPlayService.stopPlay(principal.getName()),HttpStatus.OK);
    }

    @PutMapping("/{userPlayId}")
    public ResponseEntity<UserPlayResponse> updateUserPlayById(@PathVariable Integer userPlayId, Principal principal,
                                   @RequestBody @Valid UserPlayRequest userPlayRequest) {
        return new ResponseEntity<>(
                userPlayService.updateUserPlayById(userPlayId, userPlayRequest, principal.getName()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{userPlayId}")
    public ResponseEntity<Void> deleteUserPlayById(@PathVariable Integer userPlayId, Principal principal) {
        userPlayService.deleteUserPlayById(userPlayId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
