package seniorproject.api.controllers;

import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import seniorproject.models.concretes.Project;
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

    @GetMapping("/getAll")
    public DataResult<List<ProjectDto>> getAll() {
        return this.projectService.getAll();
    }


    @GetMapping("/getAllByPage")
    public DataResult<List<ProjectDto>> getAllByPage(@RequestParam(name = "pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        return this.projectService.getAll(pageNo, pageSize);
    }

    @GetMapping("/getWorkingProjects")
    public DataResult<List<ProjectDto>> getWorkingProjects() {
        return this.projectService.getWorkingProjects(true);
    }

    @GetMapping("/getArchivedProjects")
    public DataResult<List<ProjectDto>> getArchivedProjects() {
        return this.projectService.getWorkingProjects(false);
    }


    @PostMapping("/add")
    public Result add(@RequestBody ProjectDto projectDto) {
        return this.projectService.add(projectDto);
    }

    @GetMapping("/getByGroup")
    public DataResult<List<Project>> getByGroup(@RequestParam Long groupId) {
        return projectService.getAllByGroup_Id(groupId);
    }

    @GetMapping("/getSortedByNames")
    public DataResult<List<ProjectDto>> getSortedByNames() {
        return projectService.getSortedByNames();
    }

    @GetMapping("/getByTitle")
    public DataResult<List<ProjectDto>> getByTitle(@RequestParam String title) {
        return projectService.getByTitle(title);
    }

    @GetMapping("/getByAuthorName")
    public DataResult<List<ProjectDto>> findAllByAuthorNameContaining(@RequestParam String authorName) {
        return projectService.findAllByAuthorNameContaining(authorName);
    }

}
