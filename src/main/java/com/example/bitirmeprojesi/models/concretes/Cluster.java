package com.example.bitirmeprojesi.models.concretes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "clusters")
public class Cluster {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "clusters")
    private List<Student> students;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<Application> applications;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private List<Project> projects;
}
