package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.ApplicationService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.ErrorDataResult;
import seniorproject.dataAccess.abstracts.ApplicationDao;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.dataAccess.abstracts.ProjectTypeDao;
import seniorproject.models.concretes.Application;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.models.concretes.Group;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.SeniorProject;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.concretes.enums.EStatus;
import seniorproject.models.dto.*;
import seniorproject.models.dto.projectTypeRequests.ActiveSeniorProjectResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicationManager implements ApplicationService{

    ProjectDao projectDao;
    GroupDao groupDao;
    ApplicationDao applicationDao;

    ProjectTypeDao projectTypeDao;
    @Autowired
    public ApplicationManager(ProjectDao projectDao, GroupDao groupDao, ApplicationDao applicationDao,ProjectTypeDao projectTypeDao) {
        super();
        this.projectDao = projectDao;
        this.groupDao = groupDao;
        this.applicationDao = applicationDao;
        this.projectTypeDao = projectTypeDao;
    }


    @Override
    public DataResult<ApplicationDto> createApplicationByGroupToProject(CreateApplicationDto createApplicationDto) {
        Application application = new Application();
        projectDao.findById(createApplicationDto.getProjectId()).ifPresent(application::setProject);
        groupDao.findById(createApplicationDto.getGroupId()).ifPresent(application::setGroup);
        application.setStatus(EStatus.PENDING);
        applicationDao.save(application);
        return new SuccessDataResult<>(application.toApplicationDto(), "Application created successfully");
    }

    @Override
    public DataResult<ApplicationDto> changeApplicationStatus(ChangeApplicationStatusDto changeApplicationStatusDto) {
        Optional<Application> application = applicationDao.findById(changeApplicationStatusDto.getId());
        if (application.isPresent()) {
            application.get().setStatus(changeApplicationStatusDto.getStatus());
            if (changeApplicationStatusDto.getStatus().equals(EStatus.APPROVED)) {
                // TO DO: Check if the group has already approved for another project in same term.

                // If the application is approved, all other applications for the same project are rejected.
                applicationDao.findAllByProjectId(application.get().getProject().getId()).forEach(app -> {
                    if (!app.getId().equals(application.get().getId())) {
                        app.setStatus(EStatus.REJECTED);
                        applicationDao.save(app);
                    }
                });

                // If the application is approved, the project status is set to WORKING.
                application.get().getProject().setEProjectStatus(EProjectStatus.WORKING);
                applicationDao.save(application.get());

                // If the application is approved, the group is added to the project.
                application.get().getProject().setGroup(application.get().getGroup());
                projectDao.save(application.get().getProject());
            }


            return new SuccessDataResult<>(application.get().toApplicationDto(), "Application approved successfully");
        }
        return new SuccessDataResult<>(null, "Application not found");
    }

    @Override
    public DataResult<List<ApplicationDto>> getApplicationsByProfessorId(UUID professorId) {
        List<SeniorProject> projectType = projectTypeDao.findActiveProjectType();
        if (projectType == null) {
            return new DataResult<>(null, false, "Project type not found");
        }
        if(projectType.isEmpty()){
            return new DataResult<>(null, false, "There is no active senior project");
        }
        SeniorProject seniorProject = projectType.get(0);
        List<Application> applications = applicationDao.findAllByActiveProjectProfessorId(professorId, seniorProject.getId());
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        applications.forEach(application -> applicationDtos.add(application.toApplicationDto()));
        if(applicationDtos.isEmpty())
            return new ErrorDataResult<>(null, "Applications not found");
        return new SuccessDataResult<>(applicationDtos, "Applications retrieved successfully");
    }

    @Override
    public DataResult<ApplicationDto> unApplyProject(UnApplyProjectDto unApplyProjectDto) {
        Group group = groupDao.findByIdAndProjectId(unApplyProjectDto.getStudentId(), unApplyProjectDto.getProjectId());
        Application application = applicationDao.findByGroupIdAndProjectId(group.getId(), unApplyProjectDto.getProjectId());
        if (application != null && application.getStatus().equals(EStatus.PENDING)){
            applicationDao.delete(application);
            return new SuccessDataResult<>(application.toApplicationDto(), "Application deleted successfully");
        }
        return new SuccessDataResult<>(null, "Application not found");
    }
}
