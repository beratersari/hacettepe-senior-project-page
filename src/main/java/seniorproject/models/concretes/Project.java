package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.dto.ProjectDto;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applications", "professors", "group"})
public class Project {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "project_id")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "project_type_id")
    @JsonBackReference
    private ProjectType projectType;

    @Column(name = "youtube_link")
    private String youtubeLink;

    @Column(name = "report_link")
    private String reportLink;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "project_professor",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Professor> professors;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Application> applications;

    @Enumerated(EnumType.STRING)
    private seniorproject.models.concretes.enums.EProjectStatus EProjectStatus;

    @ManyToMany
    @JoinTable(
            name = "project_keywords",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;

    public ProjectDto toProjectDto() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(String.valueOf(this.id));
        projectDto.setTitle(this.title);
        projectDto.setTerm(((SeniorProject)this.projectType).getTerm());
        projectDto.setYoutubeLink(this.youtubeLink);
        projectDto.setReportLink(this.reportLink);
        projectDto.setDescription(this.description);
        projectDto.setProfessorIds(this.professors.stream().map(Professor::getId).collect(Collectors.toList()));
        projectDto.setGroupId(this.group.getId());
        projectDto.setApplicationIds(this.applications.stream().map(Application::getId).collect(Collectors.toList()));
        projectDto.setProjectStatus(this.EProjectStatus.toString());
        projectDto.setKeywords(this.keywords.stream().map(Keyword::getName).collect(Collectors.toList()));
        projectDto.setProjectType(this.projectType.getName());
        return projectDto;
    }
}
