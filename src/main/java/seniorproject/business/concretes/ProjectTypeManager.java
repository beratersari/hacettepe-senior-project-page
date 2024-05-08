package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.ProjectTypeService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.ProjectTypeDao;
import seniorproject.models.concretes.ProjectType;
import seniorproject.models.concretes.SeniorProject;
import seniorproject.models.dto.projectTypeRequests.ActiveSeniorProjectResponseDto;
import seniorproject.models.dto.projectTypeRequests.CreateProjectTypeDto;
import seniorproject.models.dto.projectTypeRequests.ProjectTypeDto;
import seniorproject.models.concretes.enums.EProjectTypeStatus;
import seniorproject.models.dto.projectTypeRequests.SeniorProjectDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTypeManager implements ProjectTypeService {

    private final ProjectTypeDao projectTypeDao;

    @Autowired
    public ProjectTypeManager(ProjectTypeDao projectTypeDao) {
        super();
        this.projectTypeDao = projectTypeDao;
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
        projectTypeDao.save(projectType);
        return new DataResult<>(projectType.toProjectTypeDto(), true);
    }

    @Override
    public DataResult<ActiveSeniorProjectResponseDto> getActiveSeniorProjectTerm() {
        List<SeniorProject> projectType = projectTypeDao.findActiveProjectType();
        if (projectType == null) {
            return new DataResult<>(null, false, "Project type not found");
        }
        SeniorProject seniorProject = projectType.get(0);
        ActiveSeniorProjectResponseDto activeSeniorProjectResponseDto = new ActiveSeniorProjectResponseDto();
        activeSeniorProjectResponseDto.setTerm(seniorProject.getTerm());
        activeSeniorProjectResponseDto.setId(seniorProject.getId());
        activeSeniorProjectResponseDto.setName(seniorProject.getName());
        return new DataResult<>(activeSeniorProjectResponseDto, true);
    }

    // tarihe göre aktifliği başlatma ekle burayı güncelle
//    @Override
//    public DataResult<ProjectTypeDto> setStartDateOfActiveness(StartDateOfActivenessDto startDateOfActivenessDto) {
//        ProjectType = projectTypeDao.findById(startDateOfActivenessDto.getId()).orElse(null);
//        if (projectType == null) {
//            return new DataResult<>(null, false, "Project type not found");
//        }
//        projectType.setActiveness(EProjectTypeStatus.ACTIVE);
//        projectTypeDao.save(projectType);
//        return new DataResult<>(projectType.toProjectTypeDto(), true);
//    }
}
