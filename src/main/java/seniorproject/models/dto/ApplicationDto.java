package seniorproject.models.dto;
import seniorproject.models.concretes.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationDto {
    private long id;
    private long projectId;
    private long groupId;
    private Status status;
    private List<String> groupMembers;
    private String projectTitle;
}
