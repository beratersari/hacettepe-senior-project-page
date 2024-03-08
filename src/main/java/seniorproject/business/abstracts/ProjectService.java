package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.Result;
import seniorproject.models.concretes.Project;
import seniorproject.models.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    DataResult<List<ProjectDto>> getAll();

    Result add(ProjectDto projectDto);

    DataResult<List<ProjectDto>> getWorkingProjects();
}
