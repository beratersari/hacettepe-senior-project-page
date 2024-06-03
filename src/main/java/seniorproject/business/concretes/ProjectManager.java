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
import seniorproject.models.dto.*;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.dto.projectRequests.ProjectCreateDto;
import seniorproject.models.dto.projectRequests.ProjectDeleteDto;
import seniorproject.models.dto.projectRequests.ProjectUpdateDto;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProjectManager implements ProjectService {
    private final ProjectDao projectDao;
    private final ProfessorDao professorDao;
    private final GroupDao groupDao;
    private final ProjectTypeDao projectTypeDao;
    private final KeywordDao keywordDao;

    private final ApplicationDao applicationDao;
    private String posterPath = "../hacettepe-senior-project-page/src/main/java/seniorproject/resources/posters";



    @Autowired
    public ProjectManager(ProjectDao projectDao, ProfessorDao professorDao, GroupDao groupDao, ProjectTypeDao projectTypeDao, KeywordDao keywordDao, ApplicationDao applicationDao) {
        super();
        this.projectDao = projectDao;
        this.professorDao = professorDao;
        this.groupDao = groupDao;
        this.projectTypeDao = projectTypeDao;
        this.keywordDao = keywordDao;
        this.applicationDao = applicationDao;
    }

    public DataResult<List<ProjectDto>> searchAndSortProjects(EType searchType, String searchTerm, String sortType, Sort.Direction sortDirection, int pageNo, int pageSize,UUID sessionId) {
        if(pageNo < 1){
            return new ErrorDataResult<>("Page number cannot be less than 1");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
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


    public DataResult<List<ProjectDto>> searchActiveSeniorProjects(int pageNo, int pageSize, UUID sessionId){
        Pageable pageable = PageRequest.of(pageNo -1, pageSize, Sort.by(   "EProjectStatus").ascending());
        List<ProjectType> activeProjectTypes = projectTypeDao.findByActiveness();

        List<SeniorProject> activeSeniorProjects = activeProjectTypes.stream()
                .filter(projectType -> projectType instanceof SeniorProject)
                .map(projectType -> (SeniorProject) projectType).sorted(Comparator.comparing(SeniorProject::getTerm)).collect(Collectors.toList());

        String activeTerm = "";
        if (activeProjectTypes.isEmpty()) {
            return new ErrorDataResult<>("No active senior term found");
        }
        else{
            activeTerm = activeSeniorProjects.get(activeProjectTypes.size()-1).getTerm();
        }
        Page<Project> projectPage = projectDao.findByProjectTypeContainingIgnoreCase( activeTerm, pageable);

        return getListDataResult(pageNo, pageSize, projectPage,activeTerm, sessionId);
    }



    public DataResult<ProjectDto> getProjectByProjectId(UUID projectId)  {
        Project project = projectDao.findById(projectId).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }
        ProjectDto projectDto = project.toProjectDto();
        return new SuccessDataResult<>(projectDto, "Project listed.");
    }


    public DataResult<ProjectDto> createSeniorProjectByProfessor(ProjectCreateDto projectCreateDto){
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
        if (projectCreateDto.getProfessors() != null) {
            for (ProfessorInformationDto professor : projectCreateDto.getProfessors()) {
                professorDao.findById(professor.getId()).ifPresent(professors::add);
            }
        }
        Professor professor = professorDao.findById(projectCreateDto.getSessionId()).orElse(null);
        if(!professors.contains(professor)){
            professors.add(professor);
        }
        project.setProfessors(professors);
        project.setStudentLimit(projectCreateDto.getStudentLimit());

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

    @Override
    public DataResult<ProjectDto> updateSeniorProjectByProfessor(ProjectUpdateDto projectUpdateDto) {
        Project project = projectDao.findById(projectUpdateDto.getId()).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }
        project.setTitle(projectUpdateDto.getTitle());
        project.setDescription(projectUpdateDto.getDescription());

        if (projectUpdateDto.getKeywords() != null) {
            List<Keyword> keywords = new ArrayList<>();
            for (String keyword : projectUpdateDto.getKeywords()) {
                Keyword keywordEntity = keywordDao.findByName(keyword)
                        .orElseGet(() -> keywordDao.save(new Keyword(keyword)));
                keywords.add(keywordEntity);
            }
            project.setKeywords(keywords);
        }
        else{
            project.setKeywords(null);
        }
        List<Professor> professors = new ArrayList<>();
        for (ProfessorInformationDto professor : projectUpdateDto.getProfessors()) {
            professorDao.findById(professor.getId()).ifPresent(professors::add);
        }
        Professor professor = professorDao.findById(projectUpdateDto.getSessionId()).orElse(null);
        if(!professors.contains(professor)){
            professors.add(professor);
        }
        project.setProfessors(professors);
        if(projectUpdateDto.getGroupId() == null){
            project.setGroup(null);
        }
        else {
            Group group = groupDao.findById(projectUpdateDto.getGroupId()).orElse(null);
            project.setGroup(group);
        }
        project.setStudentLimit(projectUpdateDto.getStudentLimit());
        projectDao.save(project);

        return new SuccessDataResult<>(project.toProjectDto(), "Project updated.");
    }

    @Override
    public DataResult<ProjectDto> deleteSeniorProjectByProfessor(ProjectDeleteDto projectDeleteDto) {
        Project project = projectDao.findById(projectDeleteDto.getProjectId()).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }

        List<Application> applications = project.getApplications();
        applicationDao.deleteAll(applications);
        projectDao.delete(project);

        return new SuccessDataResult<>("Project deleted.");
    }

    @Override
    public DataResult<ProjectDto> addEmbedding(AddEmbeddingDto embeddingDto) {
        Project project = projectDao.findById(embeddingDto.getProjectId()).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }

        project.setEmbedding(embeddingDto.getEmbedding());
        projectDao.save(project);

        return new SuccessDataResult<>(project.toProjectDto(), "Embedding added.");
    }

    @Override
    public DataResult<List<ProjectDto>> getProjectsByProjectIds(List<UUID> projectIds) {
        List<Project> projects = projectDao.findAllByProjectIds(projectIds);
        if (projects == null) {
            return new ErrorDataResult<>("Projects not found.");
        }
        List<ProjectDto> projectDtos = projects.stream().map(Project::toProjectDto).collect(Collectors.toList());
        return new SuccessDataResult<>(projectDtos,  "Project listed.");

    }

    @Override
    public DataResult<ProjectDto> uploadPosterDemoLinkWebsiteLink(UploadPosterDemoLinkWebsiteLinkDto uploadPosterDemoLinkWebsiteLinkDto) {
        Project project = projectDao.findById(uploadPosterDemoLinkWebsiteLinkDto.getProjectId()).orElse(null);
        if (project == null) {
            return new ErrorDataResult<>("Project not found.");
        }

        project.setDemoLink(uploadPosterDemoLinkWebsiteLinkDto.getDemoLink());
        project.setWebsiteLink(uploadPosterDemoLinkWebsiteLinkDto.getWebsiteLink());

        String posterPath = this.posterPath + File.separator + project.getId() + ".png";
        File posterFile = new File(posterPath);
        try (OutputStream os = Files.newOutputStream(posterFile.toPath())) {
            if(uploadPosterDemoLinkWebsiteLinkDto.getPosterFile() != null && !uploadPosterDemoLinkWebsiteLinkDto.getPosterFile().isEmpty() && uploadPosterDemoLinkWebsiteLinkDto.getPosterFile().getBytes().length > 0)
            {
                os.write(uploadPosterDemoLinkWebsiteLinkDto.getPosterFile().getBytes());
            }
        } catch (IOException e) {
            return new ErrorDataResult<>("Failed to save file: " + e.getMessage());
        }

        project.setPosterPath(posterPath);
        projectDao.save(project);

        return new SuccessDataResult<>(project.toProjectDto(), "Poster, demo link and website link uploaded.");
    }

    public DataResult<List<ProjectDto>> getProjectByStudentId(UUID studentId) {
        List<Project> projects = new ArrayList<>();
        for (Group group : groupDao.findAllByStudentId(studentId)) {
            projects.addAll(projectDao.findAllByGroupId(group.getId()));
        }
        List<ProjectDto> projectDtos = projects.stream().map(project -> {
            return project.toProjectDto();
        }).collect(Collectors.toList());

        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    public DataResult<List<ProjectDto>> getProjectByProfessorId(UUID professorId) {
        List<Project> projects = projectDao.findAllByProfessorsId(professorId);
        List<ProjectDto> projectDtos = projects.stream().map(Project::toProjectDto).collect(Collectors.toList());

        return new SuccessDataResult<>(projectDtos, "Projects listed.");
    }

    private DataResult<List<ProjectDto>> getListDataResult(int pageNo, int pageSize, Page<Project> projectPage, String term, UUID sessionId) {
        List<Project> projects = projectPage.getContent();
        List<ProjectDto> projectDtos = new ArrayList<>();


        for (Project project : projects) {
            ProjectDto projectDto = project.toProjectDto();
            List<String> students = new ArrayList<>();
            List<String> professors = new ArrayList<>();
            boolean applied = false;

            for (Professor professor : project.getProfessors()) {
                professors.add(professor.getUsername());
                projectDto.setMyProject(professor.getId() == sessionId || projectDto.isMyProject());
            }

            if (project.getGroup() == null) {
                projectDto.setMyProject(projectDto.isMyProject());
            }
            else{
                for (Student student : project.getGroup().getStudents()) {
                    students.add(student.getUsername());
                    projectDto.setMyProject(student.getId() == sessionId || projectDto.isMyProject());
                }
            }

            for (Application application : project.getApplications()) {
                for(StudentInformationDto studentInformationDto : application.toApplicationDto().getGroupMembers()){
                    if(studentInformationDto.getId().equals(sessionId)){
                        applied = true;
                        break;
                    }
                }
            }
            projectDto.setStudents(students);
            List<ProfessorInformationDto> professorInformationDtos = new ArrayList<>();
            for (Professor professor : project.getProfessors()) {
                ProfessorInformationDto professorInformationDto = new ProfessorInformationDto();
                professorInformationDto.setId(professor.getId());
                professorInformationDto.setUsername(professor.getUsername());
                professorInformationDtos.add(professorInformationDto);
            }
            projectDto.setProfessors(professorInformationDtos);
            projectDto.setApplied(applied);
            projectDtos.add(projectDto);
        }
        long totalProjects = projectPage.getTotalElements();


        if (term == null) {
            return new SuccessDataResult<>(projectDtos,pageSize, totalProjects, projectPage.getTotalPages(), pageNo, null);
        }
        return new SuccessDataResult<>(projectDtos, pageSize, totalProjects, projectPage.getTotalPages(), pageNo, term);
    }
}
