package seniorproject.models.concretes;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.dto.AnnouncementDto;

import javax.persistence.*;
import java.util.Date;
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
    private Date createdDate;

    public AnnouncementDto toAnnouncementDto() {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(this.id);
        announcementDto.setTitle(this.title);
        announcementDto.setContent(this.content);
        announcementDto.setCreatedDate(this.createdDate);
        return announcementDto;
    }

}
