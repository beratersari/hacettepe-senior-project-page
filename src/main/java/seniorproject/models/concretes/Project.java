package seniorproject.models.concretes;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Group group;

    private Status status;

}
