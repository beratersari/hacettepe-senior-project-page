package seniorproject.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UrlAndImagesDto {
    private String url;
    private byte[] image;
}
