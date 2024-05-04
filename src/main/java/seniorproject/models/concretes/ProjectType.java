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

@Data
@Table(name = "project_types")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class ProjectType {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "admin_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @JoinColumn(name = "id")
    private Long id;

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
