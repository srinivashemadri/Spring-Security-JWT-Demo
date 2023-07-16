package com.srinivas.jwtdemo.controller.auth;

import com.srinivas.jwtdemo.model.AuthenticationResponse;
import com.srinivas.jwtdemo.model.SignInRequest;
import com.srinivas.jwtdemo.model.SignUpRequest;
import com.srinivas.jwtdemo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

}
