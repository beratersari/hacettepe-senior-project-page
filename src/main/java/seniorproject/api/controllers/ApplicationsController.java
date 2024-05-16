package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ApplicationService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.ApplicationDto;
import seniorproject.models.dto.ChangeApplicationStatusDto;
import seniorproject.models.dto.CreateApplicationDto;
import seniorproject.models.dto.GroupApplicationDto;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/applications")
public class ApplicationsController {

    ApplicationService applicationService;

    @Autowired
    public ApplicationsController(ApplicationService applicationService) {
        super();
        this.applicationService = applicationService;
    }

    @PostMapping("/createApplicationByGroupToProject")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public DataResult<ApplicationDto> createApplicationByGroupToProject(@RequestBody CreateApplicationDto createApplicationDto) {
        return applicationService.createApplicationByGroupToProject(createApplicationDto);
    }

    @PostMapping("/changeApplicationStatus")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<ApplicationDto> changeApplicationStatus(@RequestBody ChangeApplicationStatusDto changeApplicationStatusDto) {
        return applicationService.changeApplicationStatus(changeApplicationStatusDto);
    }

    @PostMapping("/getApplicationsByProfessorId")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ApplicationDto>> getApplicationsByProfessorId(@RequestBody UUID professorId) {
        return applicationService.getApplicationsByProfessorId(professorId);
    }
}
