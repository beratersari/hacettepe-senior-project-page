package seniorproject.models.concretes;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.concretes.enums.EStatus;
import seniorproject.models.dto.ApplicationDto;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.dto.StudentInformationDto;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "application_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    public ApplicationDto toApplicationDto() {
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setId(this.id);
        applicationDto.setProject(this.project.toProjectDto());
        applicationDto.setGroupId(this.group.getId());
        applicationDto.setStatus(this.status);
        List<StudentInformationDto> groupMembers = this.group.getStudents().stream()
                .map(student -> {
                    StudentInformationDto studentInformationDto = new StudentInformationDto();
                    studentInformationDto.setId(student.getId());
                    studentInformationDto.setUsername(student.getUsername());
                    return studentInformationDto;
                })
                .collect(Collectors.toList());
        applicationDto.setGroupMembers(groupMembers);
        applicationDto.setGroupName(this.group.getName());
        return applicationDto;
    }
}
