package seniorproject.models.dto;
import lombok.Data;
import seniorproject.models.concretes.Application;
import seniorproject.models.concretes.Project;

import java.util.List;

@Data
public class ProjectDto {

    private long id;
    private String name;
    private String term;
    private String youtubeLink;
    private String reportLink;
    private String description;
    private List<Long> professorIds;
    private long groupId;
    private List<Long> applicationIds;
    private boolean isWorking;

}
