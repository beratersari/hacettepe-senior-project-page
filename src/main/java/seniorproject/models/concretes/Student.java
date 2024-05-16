package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class Student extends User {
    @MapsId
    @Id
    @JoinColumn(name = "id")
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + getUsername() + '\'' +
                '}';
    }

}
