package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.TimelineService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.TimelineDao;
import seniorproject.models.concretes.Timeline;

import java.util.List;

@Service
public class TimelineManager implements TimelineService{
    private TimelineDao timelineDao;

    @Autowired
    public TimelineManager(TimelineDao timelineDao){
        this.timelineDao = timelineDao;
    }

    @Override
    public DataResult<List<Timeline>> getAll() {
        return null;
    }
}
