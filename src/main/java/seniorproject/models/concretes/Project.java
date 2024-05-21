package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import seniorproject.models.dto.ProfessorInformationDto;
import seniorproject.models.dto.ProjectDto;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name ="poster")
    private String poster;

    @Column(columnDefinition="TEXT")
    private String description;


    @Column(name = "embedding",columnDefinition="TEXT")
    private String embedding;

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
    private Long studentLimit;

    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Document> documents;

    public ProjectDto toProjectDto() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(this.id);
        projectDto.setTitle(this.title);
        projectDto.setTerm(((SeniorProject)this.projectType).getTerm());
        projectDto.setYoutubeLink(this.youtubeLink);
        projectDto.setDescription(this.description);
        if (this.group != null) {
            projectDto.setGroupId(this.group.getId().toString());
        }
        if(this.applications != null){
            projectDto.setApplicationIds(this.applications.stream().map(Application::getId).collect(Collectors.toList()));
        }
        projectDto.setProjectStatus(this.EProjectStatus.toString());
        if(this.keywords != null){
            projectDto.setKeywords(this.keywords.stream().map(Keyword::getName).collect(Collectors.toList()));

        }

        List<String> students = new ArrayList<>();

        if (this.getGroup() == null) {
            projectDto.setMyProject(projectDto.isMyProject());
        }
        else{
            for (Student student : this.getGroup().getStudents()) {
                students.add(student.getUsername());
            }
        }
        projectDto.setStudents(students);
        List<ProfessorInformationDto> professorInformationDtos = new ArrayList<>();
        for (Professor professor : this.getProfessors()) {
            ProfessorInformationDto professorInformationDto = new ProfessorInformationDto();
            professorInformationDto.setId(professor.getId());
            professorInformationDto.setUsername(professor.getUsername());
            professorInformationDtos.add(professorInformationDto);
        }
        projectDto.setProfessors(professorInformationDtos);
        projectDto.setStudentLimit(this.studentLimit);
        projectDto.setProjectType(this.projectType.getName());
        projectDto.setPoster(this.poster);
        return projectDto;
    }

    public ProjectDto toProjectDto(UUID sessionId){
        ProjectDto projectDto = this.toProjectDto();

        for (Professor professor : this.getProfessors()) {
            if (professor.getId().equals(sessionId)) {
                projectDto.setMyProject(true);
            }
        }
        if (this.getGroup() == null) {
            projectDto.setMyProject(projectDto.isMyProject());
        }
        else{
            for (Student student : this.getGroup().getStudents()) {
                if (student.getId().equals(sessionId)) {
                    projectDto.setMyProject(true);
                }
            }
        }
        for (Application application : this.getApplications()) {
            application.getGroup().getStudents().forEach(student -> {
                if (student.getId() == sessionId) {
                    projectDto.setApplied(true);
                }
            });
        }
        projectDto.setEmbedding(this.getEmbedding());
        return projectDto;
    }

}
