package com.example.bitirmeprojesi.business.concretes;

import com.example.bitirmeprojesi.business.abstracts.ProjectService;
import com.example.bitirmeprojesi.dataAccess.abstracts.ProjectDao;
import com.example.bitirmeprojesi.models.concretes.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManager implements ProjectService {
    private ProjectDao projectDao;

    @Autowired
    public ProjectManager(ProjectDao projectDao) {
        super();
        this.projectDao = projectDao;
    }

    @Override
    public List<Project> getAll() {
        return this.projectDao.findAll();
    }
}
