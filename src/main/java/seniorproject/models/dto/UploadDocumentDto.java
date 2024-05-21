package seniorproject.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UploadDocumentDto {
    private UUID projectId;
    private UUID timelineId;
    private MultipartFile file;
}
