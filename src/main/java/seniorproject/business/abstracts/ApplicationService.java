package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.ApplicationDto;
import seniorproject.models.dto.ChangeApplicationStatusDto;
import seniorproject.models.dto.CreateApplicationDto;
import seniorproject.models.dto.GroupApplicationDto;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    DataResult<ApplicationDto> createApplicationByGroupToProject(CreateApplicationDto createApplicationDto);

    DataResult<ApplicationDto> changeApplicationStatus(ChangeApplicationStatusDto applicationDto);

    DataResult<List<ApplicationDto>> getApplicationsByProfessorId(UUID professorId);
}
