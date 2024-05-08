package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.projectTypeRequests.*;

import java.util.List;

public interface ProjectTypeService {
    DataResult<List<ProjectTypeDto>> getProjectTypes();

    DataResult<ProjectTypeDto> createProjectType(CreateProjectTypeDto projectTypeDto);

    DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm();

    //DataResult<ProjectTypeDto> setStartDateOfActiveness(StartDateOfActivenessDto startDateOfActivenessDto);
}
