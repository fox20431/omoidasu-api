package com.hihusky.omoidasu.service;

import com.hihusky.omoidasu.dto.*;
import com.hihusky.omoidasu.entity.User;
import com.hihusky.omoidasu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        User user = jwtService.getUserFromRefreshToken(request.getRefreshToken());
        var accessToken = jwtService.generateAccessToken(user);
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
