package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.*;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    DataResult<ApplicationDto> createApplicationByGroupToProject(CreateApplicationDto createApplicationDto);

    DataResult<ApplicationDto> changeApplicationStatus(ChangeApplicationStatusDto applicationDto);

    DataResult<List<ApplicationDto>> getApplicationsByProfessorId(UUID professorId);

    DataResult<ApplicationDto> unApplyProject(UnApplyProjectDto unApplyProjectDto);
}
