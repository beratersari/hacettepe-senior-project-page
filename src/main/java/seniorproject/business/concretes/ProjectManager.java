package seniorproject.business.concretes;

import seniorproject.business.abstracts.ProjectService;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.models.concretes.Project;
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
