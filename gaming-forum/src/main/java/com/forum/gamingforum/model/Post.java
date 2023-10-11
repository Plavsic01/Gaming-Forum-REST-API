package com.forum.gamingforum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Lob
    private String content;
    private Long author;
    @ManyToOne
    private Thread thread;
    @Column
    private Long numberOfViews;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateAndTimeOfCreation;

    @PrePersist
    protected void onCreate(){
        this.dateAndTimeOfCreation = LocalDateTime.now();
    }
}
