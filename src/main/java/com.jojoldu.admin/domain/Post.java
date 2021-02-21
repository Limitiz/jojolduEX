package com.jojoldu.admin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;


import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;;
        this.author = author;
    }
}