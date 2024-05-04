package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ApplicationService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.ApplicationDto;

import java.util.List;

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

    @PostMapping("/getProjectByProfessorId")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public DataResult<List<ApplicationDto>> getProjectById(Long professorId) {
        return this.applicationService.getApplicationsByProfessorId(professorId);
    }
}
