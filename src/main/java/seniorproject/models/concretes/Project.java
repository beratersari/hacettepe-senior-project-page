package seniorproject.models.concretes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import seniorproject.models.dto.ProjectDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications", "professors", "group"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String term;

    @Column(name = "youtube_link")
    private String youtubeLink;
    @Column(name = "report_link")
    private String reportLink;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "project_professor",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private List<Professor> professors;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Application> applications;

    @Column(name = "isworking")
    private boolean isWorking;

    public ProjectDto toProjectDto() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(this.id);
        projectDto.setName(this.name);
        projectDto.setTerm(this.term);
        projectDto.setYoutubeLink(this.youtubeLink);
        projectDto.setReportLink(this.reportLink);
        projectDto.setDescription(this.description);
        projectDto.setProfessorIds(this.professors.stream().map(Professor::getId).collect(Collectors.toList()));
        projectDto.setGroupId(this.group.getId());
        projectDto.setApplicationIds(this.applications.stream().map(Application::getId).collect(Collectors.toList()));
        return projectDto;
    }


}
