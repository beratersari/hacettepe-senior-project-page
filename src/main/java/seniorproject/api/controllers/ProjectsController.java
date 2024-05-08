package seniorproject.api.controllers;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import seniorproject.models.concretes.enums.ERole;
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.dto.projectRequests.ProjectCreateDto;
import seniorproject.models.dto.projectRequests.ProjectRequestDto;
import seniorproject.models.dto.projectRequests.ProjectWithTypesRequestDto;
import seniorproject.models.dto.projectRequests.UserIdProjectRequestDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/projects")
public class ProjectsController {

    private final ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @PostMapping("/getProjects")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ProjectDto>> getProjects(@RequestBody ProjectRequestDto projectRequestDto) {
        UUID sessionId = projectRequestDto.getSessionId();
        if (projectRequestDto.getSearch().getType() == null || Objects.equals(projectRequestDto.getSearch().getValue(), "")) {
            String sortDirection = projectRequestDto.getSort().getDirection();
            return projectService.searchAndSortProjects(EType.TITLE, "", "id", Sort.Direction.fromString(sortDirection), projectRequestDto.getPageNumber(), projectRequestDto.getPageSize(),sessionId);
        }

        EType searchType = projectRequestDto.getSearch().getType();
        String searchTerm = projectRequestDto.getSearch().getValue();
        String sortType = projectRequestDto.getSort().getType();
        String sortDirection = projectRequestDto.getSort().getDirection();
        int pageNumber = projectRequestDto.getPageNumber();
        int pageSize = projectRequestDto.getPageSize();
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return projectService.searchAndSortProjects(searchType,searchTerm, sortType, direction, pageNumber, pageSize,sessionId);

    }

    @PostMapping("/searchSeniorProjectWithTerm")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ProjectDto>> searchSeniorProjectWithTerm(@RequestBody ProjectWithTypesRequestDto projectWithTypesRequest) {
        String searchTerm = projectWithTypesRequest.getSearchTerm();
        int pageNumber = projectWithTypesRequest.getPageNumber();
        int pageSize = projectWithTypesRequest.getPageSize();
        UUID sessionId = projectWithTypesRequest.getSessionId();
        return projectService.searchProjectsWithTypes(searchTerm, pageNumber, pageSize, sessionId);
    }

    @PostMapping("/getActiveSeniorProjects")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ProjectDto>> getActiveSeniorProjects(@RequestBody ProjectWithTypesRequestDto projectWithTypesRequest) {
        int pageNumber = projectWithTypesRequest.getPageNumber();
        int pageSize = projectWithTypesRequest.getPageSize();
        UUID sessionId = projectWithTypesRequest.getSessionId();
        return projectService.searchActiveSeniorProjects(pageNumber, pageSize, sessionId);
    }

    @PostMapping("/getProjectByProjectId")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public DataResult<ProjectDto> getProjectByProjectId(UUID projectId) {
        return this.projectService.getProjectByProjectId(projectId);
    }

    @PostMapping("/getMyProjects")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR')")
    public DataResult<List<ProjectDto>> getProjectById(@RequestBody UserIdProjectRequestDto userIdProjectRequestDto) {
        UUID sessionId = userIdProjectRequestDto.getSessionId();

        ERole[] roles = userIdProjectRequestDto.getRoles();

        return this.projectService.getMyProjects(sessionId, roles);
    }

    @PostMapping("/createSeniorProjectByProfessor")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<ProjectDto> createSeniorProjectByProfessor(@RequestBody ProjectCreateDto projectCreateDto) {
        return this.projectService.createSeniorProjectByProfessor(projectCreateDto);
    }

}
