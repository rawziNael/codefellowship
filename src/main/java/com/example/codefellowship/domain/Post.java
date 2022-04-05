package com.example.codefellowship.domain;

import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String body;
    public String createdAt;


    @ManyToOne
    public ApplicationUser applicationUser;

    public Post() {
    }

    public Post(String body, String createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }
}
