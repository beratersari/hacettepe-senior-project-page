package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.ApplicationService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.models.concretes.Application;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.models.concretes.Project;
import seniorproject.models.dto.ApplicationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationManager implements ApplicationService{

    ProjectDao projectDao;

    @Autowired
    public ApplicationManager(ProjectDao projectDao) {
        super();
        this.projectDao = projectDao;
    }


    @Override
    public DataResult<List<ApplicationDto>> getApplicationsByProfessorId(UUID professorId) {
        List<Project> projects = projectDao.findAllByProfessorsId(professorId);
        List<ApplicationDto> applications = new ArrayList<>();

        for(Project project : projects){
            project.getApplications().forEach(application -> {
                ApplicationDto applicationDto = application.toApplicationDto();
                applications.add(applicationDto);
            });
        }
        return new SuccessDataResult<>(applications);
    }
}
