package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Timeline;

import java.util.UUID;

public interface TimelineDao extends JpaRepository<Timeline, UUID>{
}
