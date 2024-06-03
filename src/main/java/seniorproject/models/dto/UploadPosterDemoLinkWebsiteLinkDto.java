package seniorproject.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UploadPosterDemoLinkWebsiteLinkDto {
    private UUID projectId;
    private MultipartFile posterFile;
    private String demoLink;
    private String websiteLink;

}
