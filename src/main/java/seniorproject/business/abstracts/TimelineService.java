package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.Timeline;

import java.util.List;

public interface TimelineService {

    DataResult<List<Timeline>> getAll();
}
