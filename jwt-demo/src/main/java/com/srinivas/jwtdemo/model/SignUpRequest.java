package com.srinivas.jwtdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Long phoneNumber;
}
