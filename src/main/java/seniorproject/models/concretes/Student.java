package seniorproject.models.concretes;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany
    @JoinTable(
            name = "student_cluster",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "cluster_id")
    )
    private List<Cluster> clusters;

}
