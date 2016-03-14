package com.example.entities;//Created by KevinBozic on 3/10/16.

import javax.persistence.*;


@Entity
public class ClosetContents {
    @Id
    @GeneratedValue
    public int id;

    @Column(nullable = false)
    public String item;

    @Column(nullable = false)
    public String color;


    public int amount;

    @ManyToOne
    public User user;


    public ClosetContents(String item, String color, int amount) {
        this.item = item;
        this.color = color;
        this.amount = amount;
    }

    public ClosetContents() {
    }
}
