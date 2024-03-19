package com.gamermetrics.GamerMetrics.auth;

import com.gamermetrics.GamerMetrics.token.Token;
import com.gamermetrics.GamerMetrics.token.TokenRepository;
import com.gamermetrics.GamerMetrics.token.TokenType;
import com.gamermetrics.GamerMetrics.user.Role;
import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        String email = request.getEmail();
        String nickName = request.getNickName();
        log.info("in register method, findByEmail performing");
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BadCredentialsException("Such email is already used");
        }
        log.info("in register method, findByNickName performing");
        if (userRepository.findByNickName(nickName).isPresent()) {
            throw new BadCredentialsException("Such nickname is already taken");
        }

        var user = User.builder()
                .nickName(request.getNickName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        String email = request.getEmail();
        log.info("in authenticate method, findByEmail performing");
        var user = userRepository.findByEmail(email)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findALlValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.Bearer)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
