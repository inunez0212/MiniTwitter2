package com.example.minitwitter.response;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ResponseAuth implements Serializable {

    private String token;
    private String username;
    private String email;
    private String photoUrl;
    private String created;
    private Boolean active;

}
