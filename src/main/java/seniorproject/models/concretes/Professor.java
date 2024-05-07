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
@Table(name = "professors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "projects"})
public class Professor extends User {
    @MapsId
    @Id
    @JoinColumn(name = "id")
    private UUID id;

    @ManyToMany(mappedBy = "professors")
    private List<Project> projects;
}
