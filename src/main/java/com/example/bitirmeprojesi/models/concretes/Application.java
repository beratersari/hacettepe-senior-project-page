package com.example.bitirmeprojesi.models.concretes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Data
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;
}
