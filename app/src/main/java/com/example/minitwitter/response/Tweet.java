
package com.example.minitwitter.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Tweet implements Serializable {

    public Tweet(){

    }

    private Integer id;
    private String mensaje;
    private List<Like> likes = new ArrayList<>();
    private User user;

    public Tweet(Integer id, String msg, List<Like> likes, User user) {
        this.id = id;
        this.mensaje = msg;
        this.likes = likes;
        this.user = user;
    }
}
