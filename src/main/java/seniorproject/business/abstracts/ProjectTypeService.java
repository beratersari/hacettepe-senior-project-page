package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.projectTypeRequests.*;

import java.util.List;
import java.util.UUID;

public interface ProjectTypeService {
    DataResult<List<ProjectTypeDto>> getProjectTypes();

    DataResult<ProjectTypeDto> createProjectType(CreateProjectTypeDto projectTypeDto);

    DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm();

    DataResult<SeniorProjectDto> activateSeniorProjectTerm(ActivateSeniorProjectRequest activateSeniorProjectRequest);

    DataResult<SeniorProjectDto> archiveSeniorProjectTerm(ArchiveSeniorProjectRequest archiveSeniorProjectRequest);

    DataResult<SeniorProjectDto> createSeniorProjectTerm(CreateSeniorProjectTermDto createSeniorProjectRequest);

    DataResult<SeniorProjectDto> editSeniorProjectTerm(EditSeniorProjectTermDto editSeniorProjectRequest);

    DataResult<SeniorProjectDto> deactivateSeniorProjectTerm(DeactivateSeniorProjectRequest deactivateSeniorProjectRequest);

    DataResult<SeniorProjectDto> getSeniorProjectWithProjectTypeId(UUID projectTypeId);
}
