package seniorproject.models.concretes;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
