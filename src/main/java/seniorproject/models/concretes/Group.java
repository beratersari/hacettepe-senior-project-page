package seniorproject.models.concretes;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Application> applications;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Project> projects;
}
