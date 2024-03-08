package seniorproject.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import seniorproject.models.dto.ProjectDto;

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

    @PostMapping("/getall")
    public DataResult<List<ProjectDto>> getAll() {
        return this.projectService.getAll();
    }

    @PostMapping("/getWorkingProjects")
    public DataResult<List<ProjectDto>> getWorkingProjects() {
        return this.projectService.getWorkingProjects();
    }

    @GetMapping("/add")
    public Result add(@RequestBody ProjectDto projectDto) {
        return this.projectService.add(projectDto);
    }

    @GetMapping("/update")
    public Result update(@RequestBody ProjectDto projectDto) {
        // TODO: Implement update method
        return this.projectService.add(projectDto);
    }
}
