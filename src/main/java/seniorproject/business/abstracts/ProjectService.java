package seniorproject.business.abstracts;

import org.springframework.data.domain.Sort;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.dto.projectRequests.ProjectCreateDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    DataResult<List<ProjectDto>> searchAndSortProjects(EType searchType, String searchTerm, String sortType, Sort.Direction sortDirection, int pageNumber, int pageSize, UUID sessionId);
    DataResult<List<ProjectDto>> searchProjectsWithTypes(String searchTerm, int pageNumber, int pageSize, UUID sessionId);
    DataResult<List<ProjectDto>> searchActiveSeniorProjects(int pageNumber, int pageSize, UUID sessionId);
    DataResult<List<ProjectDto>> getProjectByStudentId(UUID studentId);
    DataResult<List<ProjectDto>> getProjectByProfessorId(UUID professorId);
    DataResult<ProjectDto> getProjectByProjectId(UUID projectId);
    DataResult<ProjectDto> createSeniorProjectByProfessor(ProjectCreateDto projectCreateDto);
}
