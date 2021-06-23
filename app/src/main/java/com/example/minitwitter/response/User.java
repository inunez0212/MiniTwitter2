
package com.example.minitwitter.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

    private Integer id;
    private String username;
    private String descripcion;
    private String website;
    private String photoUrl;
    private String created;
}
