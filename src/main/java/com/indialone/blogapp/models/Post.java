package com.indialone.blogapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    private String title;

    @Column(length = 2000)
    private String content;

    private String image;

    private Date date;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

}
