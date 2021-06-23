
package com.example.minitwitter.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Tweet implements Serializable {

    private Integer id;
    private String mensaje;
    private List<Like> likes = new ArrayList<>();
    private User user;
}
