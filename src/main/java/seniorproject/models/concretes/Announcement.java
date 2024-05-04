package seniorproject.models.concretes;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "announcements")
public class Announcement {
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
    private long id;
    private String title;
    private String content;
    private String date;

}
