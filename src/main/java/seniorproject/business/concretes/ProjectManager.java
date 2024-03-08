package seniorproject.business.concretes;

import org.modelmapper.ModelMapper;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.Result;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.core.utilities.results.SuccessResult;
import seniorproject.dataAccess.abstracts.ApplicationDao;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.ProfessorDao;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.models.concretes.Application;
import seniorproject.models.concretes.Group;
import seniorproject.models.concretes.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.models.dto.ProjectDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectManager implements ProjectService {
    private final ProjectDao projectDao;
    private final ProfessorDao professorDao;

    private final GroupDao groupDao;
    private final ApplicationDao applicationDao;

    @Autowired
    public ProjectManager(ProjectDao projectDao, ProfessorDao professorDao, GroupDao groupDao, ApplicationDao applicationDao) {
        super();
        this.projectDao = projectDao;
        this.professorDao = professorDao;
        this.groupDao = groupDao;
        this.applicationDao = applicationDao;
    }

    @Override
    public DataResult<List<ProjectDto>> getAll() {
        List<Project> projects = projectDao.findAll();
        List<ProjectDto> projectDtos = projects.stream()
                .map(Project::toProjectDto)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    @Override
    public Result add(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setTerm(projectDto.getTerm());
        project.setYoutubeLink(projectDto.getYoutubeLink());
        project.setReportLink(projectDto.getReportLink());
        project.setDescription(projectDto.getDescription());
        project.setProfessors(professorDao.findAllById(projectDto.getProfessorIds()));
        project.setGroup(groupDao.findById(projectDto.getGroupId()).orElse(null));
        project.setApplications(applicationDao.findAllById(projectDto.getApplicationIds()));
        projectDao.save(project);

        return new SuccessResult("Project added.");
    }

    @Override
    public DataResult<List<ProjectDto>> getWorkingProjects() {
        List<Project> projects = projectDao.findAllByIsWorking(true);
        List<ProjectDto> projectDtos = projects.stream()
                .map(Project::toProjectDto)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(projectDtos, "Working projects listed.");
    }


}
