package com.srinivas.jwtdemo.service;

import com.srinivas.jwtdemo.config.JWTService;
import com.srinivas.jwtdemo.entity.UserEntity;
import com.srinivas.jwtdemo.model.AuthenticationResponse;
import com.srinivas.jwtdemo.model.Role;
import com.srinivas.jwtdemo.model.SignInRequest;
import com.srinivas.jwtdemo.model.SignUpRequest;
import com.srinivas.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthenticationResponse signUp(SignUpRequest signUpRequest){
        UserEntity userEntity = UserEntity.builder()
                .userFirstName(signUpRequest.getFirstName())
                .userLastName(signUpRequest.getLastName())
                .userEmail(signUpRequest.getEmail())
                .userName(signUpRequest.getUserName())
                .userPassword(passwordEncoder.encode(signUpRequest.getPassword()))
                .userMobileNumber(signUpRequest.getPhoneNumber())
                .role(Role.USER)
                .build();
        userRepository.save(userEntity);

        String jwtToken =jwtService.generateToken(userEntity);

        return AuthenticationResponse.builder().jwtToken(jwtToken).build();
    }

    public AuthenticationResponse signIn(SignInRequest signInRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getUserName(),
                            signInRequest.getPassword()
                    )
            );
            UserEntity userEntity = userRepository.findByUserName(signInRequest.getUserName()).orElseThrow();
            String jwtToken = jwtService.generateToken(userEntity);
            return AuthenticationResponse.builder().jwtToken(jwtToken).build();
        }
        catch (Exception e){
            System.out.println("Exception: " + e + " Message = " + e.getMessage());
            return null;
        }

    }

}
