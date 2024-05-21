package seniorproject.models.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UrlAndImagesRequest {

    private String url;
    private MultipartFile image;
}
