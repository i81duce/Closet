package com.example.entities;//Created by KevinBozic on 3/10/16.

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String password;

    public User() {
    }

    public String getPassword() {
        return password;
    }
}
