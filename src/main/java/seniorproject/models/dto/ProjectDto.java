package seniorproject.models.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDto {
    private UUID id;
    private String title;
    private String term;
    private String youtubeLink;
    private String reportLink;
    private String description;
    private String groupId;
    private List<UUID> applicationIds;
    private String projectStatus;
    private boolean applied;
    private boolean myProject;
    private String ProjectType;
    //private List<String> authorNames = new ArrayList<>();
    private List<String> students = new ArrayList<>();
    private List<ProfessorInformationDto> professors = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();
    private Long studentLimit;
    private String poster;
    private String embedding;
    private String projectTypeId;
}
