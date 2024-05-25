package seniorproject.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Data
public class DocumentDto {
    private UUID id;
    private UUID timelineId;
    private UUID projectId;
    private Date deliveryDate;
    private String deliveryName;
    private byte[] file;
    private String documentName;
    private String grade;
}
