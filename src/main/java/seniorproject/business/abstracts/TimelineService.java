package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.Timeline;
import seniorproject.models.dto.TimelineDto;

import java.util.List;
import java.util.UUID;

public interface TimelineService {

    DataResult<List<Timeline>> getAll();

    DataResult<List<TimelineDto>> getByProjectTypeId(UUID projectTypeId);
}
