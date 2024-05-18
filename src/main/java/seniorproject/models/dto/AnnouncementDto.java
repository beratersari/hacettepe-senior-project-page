package seniorproject.models.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AnnouncementDto {
    private UUID id;
    private String title;
    private String content;
    private Date createdDate;
}
