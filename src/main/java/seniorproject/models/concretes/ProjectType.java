package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.concretes.enums.EProjectTypeStatus;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "project_types")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class ProjectType {
    @Id
    @GeneratedValue(generator = "uuid2")
    @JoinColumn(name = "id")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EProjectTypeStatus activeness;

    @OneToMany(mappedBy = "projectType")
    @JsonManagedReference
    private List<Timeline> timelines;

    @OneToMany(mappedBy = "projectType")
    @JsonManagedReference
    private List<Project> projects;
}
