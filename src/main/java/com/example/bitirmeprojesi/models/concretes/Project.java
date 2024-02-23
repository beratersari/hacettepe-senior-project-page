package com.example.bitirmeprojesi.models.concretes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String term;

    @Column(name = "youtube_link")
    private String youtubeLink;
    @Column(name = "report_link")
    private String reportLink;
    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    private Status status;

}
