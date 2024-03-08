package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import seniorproject.models.dto.GroupDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "groups")
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
