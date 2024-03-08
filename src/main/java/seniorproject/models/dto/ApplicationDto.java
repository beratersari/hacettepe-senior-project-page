package seniorproject.models.dto;
import seniorproject.models.concretes.Status;
import lombok.Data;

@Data
public class ApplicationDto {
    private long id;
    private long projectId;
    private long groupId;
    private Status status;
}
