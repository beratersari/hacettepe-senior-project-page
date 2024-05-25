package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.TimelineService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.TimelineDao;
import seniorproject.models.concretes.Timeline;
import seniorproject.models.dto.TimelineDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimelineManager implements TimelineService{
    private final TimelineDao timelineDao;

    @Autowired
    public TimelineManager(TimelineDao timelineDao){
        this.timelineDao = timelineDao;
    }

    @Override
    public DataResult<List<Timeline>> getAll() {
        return null;
    }

    @Override
    public DataResult<List<TimelineDto>> getByProjectTypeId(UUID projectTypeId) {
        List<Timeline> timelines = timelineDao.getByProjectTypeId(projectTypeId);

        List<TimelineDto> timelineDtos = new ArrayList<>();
        for (Timeline timeline : timelines) {
            timelineDtos.add(timeline.toTimelineDto());
        }

        if (timelineDtos.isEmpty()) {
            return new DataResult<>(null, false, "Timeline not found");
        }
        return new DataResult<>(timelineDtos, true);
    }
}
