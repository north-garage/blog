package io.north.garage.blog.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String passwordHash;
}
