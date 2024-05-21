package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.UrlAndImages;

import java.util.UUID;

public interface UrlAndImagesDao extends JpaRepository<UrlAndImages, UUID>{
}
