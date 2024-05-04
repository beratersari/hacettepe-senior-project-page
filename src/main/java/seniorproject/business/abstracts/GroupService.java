package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.GroupDto;

import java.util.List;

public interface GroupService {

    DataResult<List<GroupDto>> getGroupByStudentId(Long studentId);
}
