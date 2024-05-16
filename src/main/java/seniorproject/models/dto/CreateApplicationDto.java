package seniorproject.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateApplicationDto {
    private UUID projectId;
    private UUID groupId;
}
