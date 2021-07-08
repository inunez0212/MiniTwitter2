package com.example.minitwitter.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestCreateTweet implements Serializable {

    private String mensaje;
}
