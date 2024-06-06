package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Timeline;

import java.util.List;
import java.util.UUID;

public interface TimelineDao extends JpaRepository<Timeline, UUID>{
    List<Timeline> getByProjectTypeId(UUID projectTypeId);

    Timeline getById(UUID timelineId);

}
