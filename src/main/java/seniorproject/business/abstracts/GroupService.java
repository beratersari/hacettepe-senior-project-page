package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.GroupApplicationDto;
import seniorproject.models.dto.GroupDto;
import seniorproject.models.dto.StudentGroupCreateRequestDto;
import seniorproject.models.dto.UpdateGroupDto;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    DataResult<List<GroupDto>> getGroupByStudentId(UUID studentId);

    DataResult<GroupDto> createStudentGroup(StudentGroupCreateRequestDto studentGroupCreateRequestDto);

    DataResult<GroupDto> deleteStudentGroup(UUID groupId);

    DataResult<GroupDto> updateStudentGroup(UpdateGroupDto groupDto);
}
