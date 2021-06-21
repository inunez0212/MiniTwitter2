package com.example.minitwitter.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestSignUp implements Serializable {

    private String email;

    private String password;

    private String username;

    private String code;
}
