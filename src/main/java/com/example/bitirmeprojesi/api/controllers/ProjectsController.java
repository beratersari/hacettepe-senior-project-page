package com.example.bitirmeprojesi.api.controllers;

import com.example.bitirmeprojesi.business.abstracts.ProjectService;
import com.example.bitirmeprojesi.models.concretes.Project;
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
