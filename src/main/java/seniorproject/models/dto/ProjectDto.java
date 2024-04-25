package seniorproject.models.dto;
import lombok.Data;

import java.util.ArrayList;
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
    private String projectStatus;
    private List<String> authorNames = new ArrayList<>();

}
