package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.Result;
import seniorproject.models.concretes.Project;
import seniorproject.models.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    DataResult<List<ProjectDto>> getAll();
    DataResult<List<ProjectDto>> getAll(int pageNo, int pageSize);
    Result add(ProjectDto projectDto);
    DataResult<List<ProjectDto>> getWorkingProjects(Boolean working);

    DataResult<List<Project>> getAllByGroup_Id(Long groupId);

    DataResult<List<ProjectDto>> getSortedByNames();

    DataResult<List<ProjectDto>> getByTitle(String title);

    DataResult<List<ProjectDto>> findAllByAuthorNameContaining(String authorName);
    DataResult<List<ProjectDto>> getByKeywords(String keyword);
}
