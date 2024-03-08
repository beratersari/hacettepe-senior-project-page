package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import seniorproject.models.dto.ProfessorDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "professors")
    private List<Project> projects;
}
