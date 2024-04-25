package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "professors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "projects"})
public class Professor extends User {
    @MapsId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JoinColumn(name = "id")
    private long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @ManyToMany(mappedBy = "professors")
    private List<Project> projects;
}
