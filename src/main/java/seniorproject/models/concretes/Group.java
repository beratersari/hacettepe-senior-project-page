package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import seniorproject.models.dto.GroupDto;
import seniorproject.models.dto.StudentInformationDto;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Application> applications;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Project> projects;

    public GroupDto toGroupDto(){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(this.getId());
        groupDto.setGroupName(this.name);
        if(this.students != null){
            List<StudentInformationDto> studentInformationDtos = new ArrayList<>();

            for(Student student : this.students){
                StudentInformationDto studentInformationDto = new StudentInformationDto();
                studentInformationDto.setId(student.getId());
                studentInformationDto.setUsername(student.getUsername());
                studentInformationDtos.add(studentInformationDto);
            }
            // burada printlerken bir hata oluyor, loopa giriyor? sonra tekrar bak
            //System.out.println(this.students);
            groupDto.setGroupMembers(studentInformationDtos);
            System.out.println(groupDto.getGroupMembers());
        }
        if(this.applications != null)
        {
            groupDto.setApplications(this.applications.stream().map(Application::toApplicationDto).collect(Collectors.toList()));
        }
        if(this.projects != null){
            groupDto.setProjects(this.projects.stream().map(Project::toProjectDto).collect(Collectors.toList()));
        }
        return groupDto;
    }
    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}

