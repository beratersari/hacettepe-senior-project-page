package seniorproject.dataAccess.abstracts;
import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Announcement;

import java.util.List;
import java.util.UUID;

public interface AnnouncementDao extends JpaRepository<Announcement, UUID> {

    List<Announcement> findAllByOrderByCreatedDateDesc();
}
