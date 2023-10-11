package com.forum.gamingforum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "threads")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Long author;
    @OneToMany(mappedBy = "thread",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateAndTimeOfCreation;
    @Column
    private Long numberOfViews;
    @ManyToOne
    private Category category;

    @PrePersist
    protected void onCreate(){
        this.dateAndTimeOfCreation = LocalDateTime.now();
    }

}
