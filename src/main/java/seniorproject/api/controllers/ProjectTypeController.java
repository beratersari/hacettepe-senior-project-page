package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectTypeService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.projectTypeRequests.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/projectTypes")
public class ProjectTypeController {

    private final ProjectTypeService projectTypeService;

    @Autowired
    public ProjectTypeController(ProjectTypeService projectTypeService) {
        super();
        this.projectTypeService = projectTypeService;
    }


    @PostMapping("/getProjectTypes")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ProjectTypeDto>> getProjectTypes() {
        return projectTypeService.getProjectTypes();
    }

    @PostMapping("/createProjectType")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<ProjectTypeDto> createProjectType(@RequestBody CreateProjectTypeDto projectTypeDto) {
        return projectTypeService.createProjectType(projectTypeDto);
    }

    @PostMapping("/getActiveSeniorProjectTerm")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm() {
        return projectTypeService.getActiveSeniorProjectTerm();
    }

//    @PostMapping("/setStartDateOfActiveness")
//    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
//    public DataResult<ProjectTypeDto> setStartDateOfActiveness(@RequestBody StartDateOfActivenessDto startDateOfActivenessDto) {
//        return projectTypeService.setStartDateOfActiveness(startDateOfActivenessDto);
//    }
}
