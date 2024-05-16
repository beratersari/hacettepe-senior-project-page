package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.ProjectTypeService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.ProjectTypeDao;
import seniorproject.dataAccess.abstracts.TimelineDao;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.ProjectType;
import seniorproject.models.concretes.SeniorProject;
import seniorproject.models.concretes.Timeline;
import seniorproject.models.concretes.enums.EProjectStatus;
import seniorproject.models.dto.TimelineDto;
import seniorproject.models.dto.projectTypeRequests.*;
import seniorproject.models.concretes.enums.EProjectTypeStatus;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProjectTypeManager implements ProjectTypeService {

    private final ProjectTypeDao projectTypeDao;
    private final TimelineDao timelineDao;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Autowired
    public ProjectTypeManager(ProjectTypeDao projectTypeDao, TimelineDao timelineDao) {
        super();
        this.projectTypeDao = projectTypeDao;
        this.timelineDao = timelineDao;
    }
    @Override
    public DataResult<List<ProjectTypeDto>> getProjectTypes() {
        List<ProjectType> projectTypes = projectTypeDao.findAll();
        List<ProjectTypeDto> projectTypeDtos = new ArrayList<>();
        for (ProjectType projectType : projectTypes) {
            projectTypeDtos.add(projectType.toProjectTypeDto());
        }
        return new DataResult<>(projectTypeDtos, true);
    }

    @Override
    public DataResult<ProjectTypeDto> createProjectType(CreateProjectTypeDto projectTypeDto) {
        ProjectType projectType = new ProjectType();
        projectType.setName(projectTypeDto.getName());
        projectType.setActiveness(EProjectTypeStatus.NOT_STARTED);

        List<Timeline> timelines = new ArrayList<>();

        for(TimelineDto timelineDto : projectTypeDto.getTimelines()){
            Timeline timeline = new Timeline();
            timeline.setDeliveryDate(timelineDto.getDeliveryDate());
            timeline.setDeliveryName(timelineDto.getDeliveryName());
            timeline.setProjectType(projectType);
        }
        projectType.setTimelines(timelines);

        projectTypeDao.save(projectType);
        return new DataResult<>(projectType.toProjectTypeDto(), true);
    }



    @Override
    public DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm() {
        List<SeniorProject> projectType = projectTypeDao.findActiveProjectType();
        if (projectType == null) {
            return new DataResult<>(null, false, "Project type not found");
        }
        if(projectType.isEmpty()){
            return new DataResult<>(null, false, "There is no active senior project");
        }
        SeniorProject seniorProject = projectType.get(0);
        ActiveSeniorProjectResponseDto activeSeniorProjectResponseDto = new ActiveSeniorProjectResponseDto();
        activeSeniorProjectResponseDto.setTerm(seniorProject.getTerm());
        activeSeniorProjectResponseDto.setId(seniorProject.getId());
        activeSeniorProjectResponseDto.setName(seniorProject.getName());
        return new DataResult<>(activeSeniorProjectResponseDto, true);
    }

    @Override
    public DataResult<SeniorProjectDto> activateSeniorProjectTerm(ActivateSeniorProjectRequest activateSeniorProjectRequest) {
        SeniorProject seniorProject = projectTypeDao.findSeniorProjectById(activateSeniorProjectRequest.getId());
        if (seniorProject == null) {
            return new DataResult<>(null, false, "Senior project not found");
        }

        List<SeniorProject> seniorProjects = projectTypeDao.findActiveProjectType();

        if(!seniorProjects.isEmpty()){
            return new DataResult<>(null, false, "There is already an active senior project, make it ARCHIVED!");
        }

        seniorProject.setActiveness(EProjectTypeStatus.ACTIVE);
        projectTypeDao.save(seniorProject);
        return new DataResult<>(seniorProject.toSeniorProjectDto(), true);
    }

    @Override
    public DataResult<SeniorProjectDto> archiveSeniorProjectTerm(ArchiveSeniorProjectRequest archiveSeniorProjectRequest) {
        SeniorProject seniorProject = projectTypeDao.findSeniorProjectById(archiveSeniorProjectRequest.getId());
        if (seniorProject == null) {
            return new DataResult<>(null, false, "Senior project not found");
        }
        seniorProject.setActiveness(EProjectTypeStatus.ARCHIVED);

        for(Project project : seniorProject.getProjects()){
            project.setEProjectStatus(EProjectStatus.ARCHIVED);
        }

        projectTypeDao.save(seniorProject);
        return new DataResult<>(seniorProject.toSeniorProjectDto(), true);
    }

    @Override
    public DataResult<SeniorProjectDto> createSeniorProjectTerm(CreateSeniorProjectTermDto createSeniorProjectRequest) {
        SeniorProject seniorProject = new SeniorProject();
        seniorProject.setName(createSeniorProjectRequest.getName());
        seniorProject.setTerm(createSeniorProjectRequest.getTerm());
        seniorProject.setActiveness(EProjectTypeStatus.NOT_STARTED);

        try {
            seniorProject = projectTypeDao.save(seniorProject);
        } catch (Exception e) {
            return new DataResult<>(null, false, "Senior project term already exists");
        }

        List<Timeline> timelines = new ArrayList<>();

        for (TimelineDto timelineDto : createSeniorProjectRequest.getTimelines()) {
            Timeline timeline = new Timeline();
            timeline.setDeliveryDate(timelineDto.getDeliveryDate());
            timeline.setDeliveryName(timelineDto.getDeliveryName());
            timeline.setProjectType(seniorProject);
            timelines.add(timeline);
        }

        timelineDao.saveAll(timelines);

        seniorProject.setTimelines(timelines);

        return new DataResult<>(seniorProject.toSeniorProjectDto(), true);
    }

    @Override
    public DataResult<SeniorProjectDto> editSeniorProjectTerm(EditSeniorProjectTermDto editSeniorProjectRequest) {
        SeniorProject seniorProject = projectTypeDao.findSeniorProjectById(editSeniorProjectRequest.getId());
        if (seniorProject == null) {
            return new DataResult<>(null, false, "Senior project not found");
        }
        seniorProject.setName(editSeniorProjectRequest.getName());
        seniorProject.setTerm(editSeniorProjectRequest.getTerm());

        List<Timeline> timelines = new ArrayList<>();

        timelineDao.deleteAll(seniorProject.getTimelines());

        for (TimelineDto timelineDto : editSeniorProjectRequest.getTimelines()) {
            Timeline timeline = new Timeline();
            timeline.setDeliveryDate(timelineDto.getDeliveryDate());
            timeline.setDeliveryName(timelineDto.getDeliveryName());
            timeline.setProjectType(seniorProject);
            timelines.add(timeline);
        }

        timelineDao.saveAll(timelines);

        seniorProject.setTimelines(timelines);

        projectTypeDao.save(seniorProject);

        return new DataResult<>(seniorProject.toSeniorProjectDto(), true);
    }


}
