package com.hihusky.omoidasu.controller;

import com.hihusky.omoidasu.dto.LoginRequest;
import com.hihusky.omoidasu.dto.AuthenticationResponse;
import com.hihusky.omoidasu.dto.RegisterRequest;
import com.hihusky.omoidasu.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * check username availability
      */
    @GetMapping("/{username}/check")
    public ResponseEntity<Map<String, Object>> checkUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(userService.checkUsername(username));
    }

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(userService.login(request));
    }
}
