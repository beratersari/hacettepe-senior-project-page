package seniorproject.models.concretes;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.concretes.enums.Status;
import seniorproject.models.dto.ApplicationDto;

import javax.persistence.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "applications")
public class Application {
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
    @Column(name = "application_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ApplicationDto toApplicationDto() {
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setId(this.id);
        applicationDto.setProjectId(this.project.getId());
        applicationDto.setProjectTitle(this.project.getTitle());
        applicationDto.setGroupId(this.group.getId());
        applicationDto.setStatus(this.status);
        if (this.group.getStudents() != null) {
            applicationDto.setGroupMembers(this.group.getStudents().stream().map(Student::getUsername).collect(Collectors.toList()));
        }
        return applicationDto;
    }
}
