package seniorproject.api.controllers;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.dto.ProjectRequestDto;

import java.util.List;

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
        if (projectRequestDto.getSearch() == null || !projectRequestDto.getSort().getType().equals("title")) {
            return projectService.searchAndSortProjects(EType.TITLE, "", "id", Sort.Direction.ASC, projectRequestDto.getPageNo(), projectRequestDto.getPageSize());
        }

        EType searchType = projectRequestDto.getSearch().getType();
        String searchTerm = projectRequestDto.getSearch().getValue();
        String sortType = projectRequestDto.getSort().getType();
        String sortDirection = projectRequestDto.getSort().getDirection();
        int pageNumber = projectRequestDto.getPageNo();
        int pageSize = projectRequestDto.getPageSize();

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return projectService.searchAndSortProjects(searchType,searchTerm, sortType, direction, pageNumber, pageSize);

    }


}
