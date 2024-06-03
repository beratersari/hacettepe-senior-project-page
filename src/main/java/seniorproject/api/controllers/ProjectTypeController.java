package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectTypeService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.projectTypeRequests.*;

import java.util.List;
import java.util.UUID;

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<List<ProjectTypeDto>> getProjectTypes() {
        return projectTypeService.getProjectTypes();
    }

    @PostMapping("/createProjectType")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<ProjectTypeDto> createProjectType(@RequestBody CreateProjectTypeDto projectTypeDto) {
        return projectTypeService.createProjectType(projectTypeDto);
    }

    @PostMapping("/getActiveSeniorProjectTerm")
    public DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm() {
        return projectTypeService.getActiveSeniorProjectTerm();
    }

    @PostMapping("/activateSeniorProjectTerm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    public DataResult<SeniorProjectDto> activateSeniorProjectTerm(@RequestBody ActivateSeniorProjectRequest activateSeniorProjectRequest) {
        return projectTypeService.activateSeniorProjectTerm(activateSeniorProjectRequest);
    }


    @PostMapping("/archiveSeniorProjectTerm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<SeniorProjectDto> archiveSeniorProjectTerm(@RequestBody ArchiveSeniorProjectRequest archiveSeniorProjectRequest) {
        return projectTypeService.archiveSeniorProjectTerm(archiveSeniorProjectRequest);
    }

    @PostMapping("/createSeniorProjectTerm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<SeniorProjectDto> createSeniorProjectTerm(@RequestBody CreateSeniorProjectTermDto createSeniorProjectRequest) {
        return projectTypeService.createSeniorProjectTerm(createSeniorProjectRequest);
    }

    @PostMapping("/editSeniorProjectTerm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<SeniorProjectDto> editSeniorProjectTerm(@RequestBody EditSeniorProjectTermDto editSeniorProjectRequest) {
        return projectTypeService.editSeniorProjectTerm(editSeniorProjectRequest);
    }

    @PostMapping("/deactivateSeniorProjectTerm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<SeniorProjectDto> deactivateSeniorProjectTerm(@RequestBody DeactivateSeniorProjectRequest deactivateSeniorProjectRequest) {
        return projectTypeService.deactivateSeniorProjectTerm(deactivateSeniorProjectRequest);
    }

    @PostMapping("/getSeniorProjectWithProjectTypeId")
    public DataResult<SeniorProjectDto> getSeniorProjectWithProjectTypeId(@RequestBody UUID projectTypeId) {
        return projectTypeService.getSeniorProjectWithProjectTypeId(projectTypeId);
    }
}
