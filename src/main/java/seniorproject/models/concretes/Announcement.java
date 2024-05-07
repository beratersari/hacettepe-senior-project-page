package seniorproject.models.concretes;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private String title;
    private String content;
    private String date;

}
