package seniorproject.models.concretes;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private String date;

}
