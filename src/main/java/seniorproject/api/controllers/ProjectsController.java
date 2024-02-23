package seniorproject.api.controllers;

import seniorproject.business.abstracts.ProjectService;
import seniorproject.models.concretes.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @GetMapping("/getall")
    public List<Project> getAll() {
        return this.projectService.getAll();
    }
}
