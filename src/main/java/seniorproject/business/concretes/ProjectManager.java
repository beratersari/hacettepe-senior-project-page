package seniorproject.business.concretes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.*;
import seniorproject.dataAccess.abstracts.ApplicationDao;
import seniorproject.dataAccess.abstracts.GroupDao;
import seniorproject.dataAccess.abstracts.ProfessorDao;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.models.concretes.Professor;
import seniorproject.models.concretes.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.models.concretes.Student;
import seniorproject.models.dto.ProjectDto;

import java.util.ArrayList;
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
    public DataResult<List<ProjectDto>> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Project> projectPage = projectDao.findAll(pageable);

        List<Project> projects = projectPage.getContent();
        List<ProjectDto> projectDtos = projects.stream()
                .map(Project::toProjectDto)
                .collect(Collectors.toList());

        long totalProjects = projectPage.getTotalElements();

        return new SuccessDataResult<>(projectDtos, pageSize, totalProjects, projectPage.getTotalPages(), pageNo);
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
    public DataResult<List<ProjectDto>> getWorkingProjects(Boolean working) {
        List<Project> projects = projectDao.findAllByIsWorking(true);
        List<ProjectDto> projectDtos = projects.stream()
                .map(Project::toProjectDto)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(projectDtos, "Working projects listed.");
    }

    @Override
    public DataResult<List<Project>> getAllByGroup_Id(Long groupId) {
        List<Project> projects = projectDao.findAllByGroup_Id(groupId);
        return new SuccessDataResult<>(projects, "Projects listed.");
    }

    @Override
    public DataResult<List<ProjectDto>> getSortedByNames() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        List<Project> projects = projectDao.findAll(sort);
        List<ProjectDto> projectDtos = projects.stream()
                .map(Project::toProjectDto)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    // Searchler yapay zeka ile güncellenecek
    @Override
    public DataResult<List<ProjectDto>> getByTitle(String title) {
        List<Project> projects = projectDao.findAllByNameLikeIgnoreCase(title);

        System.out.println(projects.size());

        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            ProjectDto projectDto = project.toProjectDto();
            List<String> authorNames = new ArrayList<>();

            for (Professor professor : project.getProfessors()) {
                authorNames.add(professor.getName());
            }

            project.getGroup().getStudents().forEach(student -> authorNames.add(student.getName()));

            projectDto.setAuthorNames(authorNames);
            projectDtos.add(projectDto);
        }

        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    @Override
    public DataResult<List<ProjectDto>> findAllByAuthorNameContaining(String authorName) {
        List<Project> projects = projectDao.findAllByAuthorNameContaining(authorName);

        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            ProjectDto projectDto = project.toProjectDto();
            List<String> authorNames = new ArrayList<>();

            for (Professor professor : project.getProfessors()) {
                authorNames.add(professor.getName());
            }

            for (Student student : project.getGroup().getStudents()) {
                authorNames.add(student.getName());
            }

            projectDto.setAuthorNames(authorNames);
            projectDtos.add(projectDto);
        }

        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    @Override
    public DataResult<List<ProjectDto>> getByKeywords(String keyword) {
        // TO DO
        return null;
    }

}
