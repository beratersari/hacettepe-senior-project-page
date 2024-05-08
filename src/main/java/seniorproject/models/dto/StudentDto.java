package seniorproject.models.dto;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class StudentDto {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Long> groupIds;
}
