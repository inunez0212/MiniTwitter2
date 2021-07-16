package com.example.minitwitter.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class TweetDelete implements Serializable {

    private String mensaje;
    private User user;

}
