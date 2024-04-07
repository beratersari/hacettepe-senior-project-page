package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications", "projects", "students"})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Application> applications;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Project> projects;
}
