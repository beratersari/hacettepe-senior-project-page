package seniorproject.models.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class StudentInformationDto {
    private UUID id;
    private String username;
}
