package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.dto.GroupDto;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications", "projects", "students"})
public class Group {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "group_id")
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Application> applications;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Project> projects;

    public GroupDto toGroupDto(){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(String.valueOf(this.id));
        groupDto.setGroupName(this.name);
        groupDto.setGroupMembers(this.students.stream().map(Student::getUsername).collect(Collectors.toList()));
        groupDto.setApplications(this.applications.stream().map(Application::toApplicationDto).collect(Collectors.toList()));
        groupDto.setProjectIds(this.projects.stream().map(project -> project.getId().toString()).collect(Collectors.toList()));
        return groupDto;
    }
}

