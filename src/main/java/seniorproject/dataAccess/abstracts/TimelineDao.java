package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Timeline;

public interface TimelineDao extends JpaRepository<Timeline, Long>{
}
