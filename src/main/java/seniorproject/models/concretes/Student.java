package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "students")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class Student extends User {
    @MapsId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JoinColumn(name = "id")
    private long id;


    @Column(nullable = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "student_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;
}
