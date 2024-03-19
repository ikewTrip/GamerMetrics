package com.gamermetrics.GamerMetrics.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{nickName}")
    public ResponseEntity<UserResponse> getUserByNickName(@PathVariable("nickName") String nickName) {

        return ResponseEntity.ok(userService.getUserByNickName(nickName));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUser(Principal principal) {

        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest, Principal principal) {
        return ResponseEntity.ok(userService.updateUser(userRequest, principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.ok().build();
    }
 
    @DeleteMapping("/{nickName}")
    public ResponseEntity<Void> deleteUserByNickName(@PathVariable("nickName") String nickName) {
        userService.deleteUserByNickName(nickName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/block/{nickName}")
    public ResponseEntity<Void> blockUser(@PathVariable("nickName") String nickName) {
        userService.blockUser(nickName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unblock/{nickName}")
    public ResponseEntity<Void> unblockUser(@PathVariable("nickName") String nickName) {
        userService.unblockUser(nickName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<Void> acceptInvite(@PathVariable("id") Integer id, Principal principal) {
        userService.acceptInvite(id, principal.getName());
        return ResponseEntity.ok().build();
    }

}
