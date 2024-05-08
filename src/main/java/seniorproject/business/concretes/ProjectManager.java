package seniorproject.business.concretes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import seniorproject.business.abstracts.ProjectService;
import seniorproject.core.utilities.results.*;
import seniorproject.dataAccess.abstracts.*;
import seniorproject.models.concretes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.models.concretes.enums.ERole;
import seniorproject.models.dto.EType;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.dto.projectRequests.ProjectCreateDto;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProjectManager implements ProjectService {
    private final ProjectDao projectDao;
    private final ProfessorDao professorDao;
    private final GroupDao groupDao;
    private final ApplicationDao applicationDao;
    private final ProjectTypeDao projectTypeDao;
    private final KeywordDao keywordDao;


    @Autowired
    public ProjectManager(ProjectDao projectDao, ProfessorDao professorDao, GroupDao groupDao, ApplicationDao applicationDao, ProjectTypeDao projectTypeDao, KeywordDao keywordDao) {
        super();
        this.projectDao = projectDao;
        this.professorDao = professorDao;
        this.groupDao = groupDao;
        this.applicationDao = applicationDao;
        this.projectTypeDao = projectTypeDao;
        this.keywordDao = keywordDao;
    }

    public DataResult<List<ProjectDto>> searchAndSortProjects(EType searchType, String searchTerm, String sortType, Sort.Direction sortDirection, int pageNo, int pageSize,UUID sessionId) {
        if(pageNo < 1){
            return new ErrorDataResult<>("Page number cannot be less than 1");
        }
        Pageable pageable;
       try{
           pageable = PageRequest.of(pageNo - 1, pageSize, sortDirection, sortType);
         }catch (IllegalArgumentException e){
           pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.ASC, "id");
       }
        Page<Project> projectPage;
        switch (searchType) {
            case TITLE:
                projectPage = projectDao.findByTitleContainingIgnoreCase(searchTerm, pageable);
                break;

            case AUTHORS:
                projectPage = projectDao.findByAuthorNameContainingIgnoreCase(searchTerm, pageable);
                break;

            case KEYWORDS:
                projectPage = projectDao.findByKeywordsContainingIgnoreCase(searchTerm, pageable);
                break;

            default:
                throw new IllegalArgumentException("Unsupported search type: " + searchType);
        }

        return getListDataResult(pageNo, pageSize, projectPage,null, sessionId);
    }

    public DataResult<List<ProjectDto>> searchProjectsWithTypes(String searchTerm, int pageNo, int pageSize, UUID sessionId) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Project> projectPage = projectDao.findByProjectTypeContainingIgnoreCase(searchTerm, pageable);
        return getListDataResult(pageNo, pageSize, projectPage, searchTerm, sessionId);
    }


    public DataResult<List<ProjectDto>> searchActiveSeniorProjects(int pageNo, int pageSize, UUID sessionId) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        List<ProjectType> activeProjectTypes = projectTypeDao.findByActiveness();

        List<SeniorProject> activeSeniorProjects = activeProjectTypes.stream()
                .filter(projectType -> projectType instanceof SeniorProject)
                .map(projectType -> (SeniorProject) projectType).sorted(Comparator.comparing(SeniorProject::getTerm)).collect(Collectors.toList());

        String activeTerm = "";
        if (activeProjectTypes.isEmpty()) {
            return new ErrorDataResult<>("No active senior projects found");
        }
        else{
            activeTerm = activeSeniorProjects.get(activeProjectTypes.size()-1).getTerm();
        }
        Page<Project> projectPage = projectDao.findByProjectTypeContainingIgnoreCase( activeTerm, pageable);
        return getListDataResult(pageNo, pageSize, projectPage,activeTerm, sessionId);
    }



    public DataResult<ProjectDto> getProjectByProjectId(UUID projectId) {
        Project project = projectDao.findById(projectId).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }
        ProjectDto projectDto = project.toProjectDto();
        return new SuccessDataResult<>(projectDto, "Project listed.");
    }


    public DataResult<ProjectDto> createSeniorProjectByProfessor(ProjectCreateDto projectCreateDto) {
        Project project = new Project();
        project.setTitle(projectCreateDto.getTitle());
        project.setDescription(projectCreateDto.getDescription());
        ProjectType projectType = projectTypeDao.findById(projectCreateDto.getProjectTypeId()).get();
        project.setProjectType(projectType);
        if (projectCreateDto.getKeywords() != null) {
            List<Keyword> keywords = new ArrayList<>();
            for (String keyword : projectCreateDto.getKeywords()) {
                Keyword keywordEntity = keywordDao.findByName(keyword)
                        .orElseGet(() -> keywordDao.save(new Keyword(keyword)));
                keywords.add(keywordEntity);
            }
            project.setKeywords(keywords);
        }
        project.setEProjectStatus(EProjectStatus.OFFERED);
        List<Professor> professors = new ArrayList<>();
        for (UUID professorId : projectCreateDto.getProfessorIds()) {
            professorDao.findById(professorId).ifPresent(professors::add);
        }
        Professor professor = professorDao.findById(projectCreateDto.getSessionId()).orElse(null);
        professors.add(professor);
        project.setProfessors(professors);
        projectDao.save(project);

        projectType.getProjects().add(project);

        return new SuccessDataResult<>(project.toProjectDto(), "Project created.");
    }


    @Override
    public DataResult<List<ProjectDto>> getMyProjects(UUID sessionId, ERole[] roles) {
        ERole role = null;
        for(ERole eRole : roles){
            if (role == ERole.ROLE_PROFESSOR){
                break;
            }
            else{
                role = eRole;
            }
        }
        if (role == ERole.ROLE_STUDENT) {
            return getProjectByStudentId(sessionId);
        }
        else if (role == ERole.ROLE_PROFESSOR) {
            return getProjectByProfessorId(sessionId);
        }
        return new ErrorDataResult<>("Not project for this role.");
    }

    public DataResult<List<ProjectDto>> getProjectByStudentId(UUID studentId) {
        List<Project> projects = new ArrayList<>();
        for (Group group : groupDao.findAllByStudentId(studentId)) {
            projects.addAll(projectDao.findAllByGroupId(group.getId()));
        }

        return new SuccessDataResult<>(projects.stream().map(Project::toProjectDto).collect(Collectors.toList()), "Projects listed.");
    }

    public DataResult<List<ProjectDto>> getProjectByProfessorId(UUID professorId) {
        List<Project> projects = projectDao.findAllByProfessorsId(professorId);
        return new SuccessDataResult<>(projects.stream().map(Project::toProjectDto).collect(Collectors.toList()), "Projects listed.");
    }

    private DataResult<List<ProjectDto>> getListDataResult(int pageNo, int pageSize, Page<Project> projectPage, String term, UUID sessionId) {
        List<Project> projects = projectPage.getContent();
        List<ProjectDto> projectDtos = new ArrayList<>();


        for (Project project : projects) {
            ProjectDto projectDto = project.toProjectDto();
            List<String> authorNames = new ArrayList<>();

            for (Professor professor : project.getProfessors()) {
                authorNames.add(professor.getUsername());
                projectDto.setMyProject(professor.getId() == sessionId || projectDto.isMyProject());
            }

            if (project.getGroup() == null) {
                projectDto.setMyProject(projectDto.isMyProject());
            }
            else{
                for (Student student : project.getGroup().getStudents()) {
                    authorNames.add(student.getUsername());
                    projectDto.setMyProject(student.getId() == sessionId || projectDto.isMyProject());
                }
            }

            for (Application application : project.getApplications()) {
                application.getGroup().getStudents().forEach(student -> {
                    if (student.getId() == sessionId) {
                        projectDto.setApplied(true);
                    }
                });
            }
            projectDto.setAuthorNames(authorNames);
            projectDtos.add(projectDto);
        }
        long totalProjects = projectPage.getTotalElements();
        if (term == null) {
            return new SuccessDataResult<>(projectDtos, pageSize, totalProjects, projectPage.getTotalPages(), pageNo, null);
        }
        return new SuccessDataResult<>(projectDtos, pageSize, totalProjects, projectPage.getTotalPages(), pageNo, term);
    }
}
