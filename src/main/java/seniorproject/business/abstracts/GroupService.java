package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.GroupDto;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    DataResult<List<GroupDto>> getGroupByStudentId(UUID studentId);
}
