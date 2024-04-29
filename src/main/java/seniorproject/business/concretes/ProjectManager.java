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
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import seniorproject.models.dto.ProjectRequestDto;


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

    public DataResult<List<ProjectDto>> searchAndSortProjects(EType searchType, String searchTerm, String sortType, Sort.Direction sortDirection, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortType));
        Page<Project> projectPage;
        switch (searchType) {
            case TITLE:
                projectPage = projectDao.findByTitleContainingIgnoreCase(searchTerm, pageable);
                break;

            case AUTHOR:
                projectPage = projectDao.findByAuthorNameContainingIgnoreCase(searchTerm, pageable);
                break;

            case KEYWORDS:
                projectPage = projectDao.findByKeywordsContainingIgnoreCase(searchTerm, pageable);
                break;

            default:
                throw new IllegalArgumentException("Unsupported search type: " + searchType);
        }

        List<Project> projects = projectPage.getContent();
        List<ProjectDto> projectDtos = new ArrayList<>();

        for (Project project : projects) {
            ProjectDto projectDto = project.toProjectDto();
            List<String> authorNames = new ArrayList<>();

            for (Professor professor : project.getProfessors()) {
                authorNames.add(professor.getUsername());
            }

            for (Student student : project.getGroup().getStudents()) {
                authorNames.add(student.getUsername());
            }

            projectDto.setAuthorNames(authorNames);
            projectDtos.add(projectDto);
        }

        long totalProjects = projectPage.getTotalElements();
        return new SuccessDataResult<>(projectDtos, pageSize, totalProjects, projectPage.getTotalPages(), pageNo);
    }

}
