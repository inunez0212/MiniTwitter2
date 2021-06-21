package com.example.minitwitter.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class RequestLogin implements Serializable {

    private String email;

    private String password;

}
