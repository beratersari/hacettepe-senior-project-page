package seniorproject.models.concretes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import seniorproject.models.dto.UrlAndImagesDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "url_and_images")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class UrlAndImages {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private String url;
    private byte[] image;

    public UrlAndImagesDto toUrlAndImagesDto() {
        UrlAndImagesDto urlAndImagesDto = new UrlAndImagesDto();
        urlAndImagesDto.setUrl(this.url);
        urlAndImagesDto.setImage(this.image);
        return urlAndImagesDto;
    }
}
