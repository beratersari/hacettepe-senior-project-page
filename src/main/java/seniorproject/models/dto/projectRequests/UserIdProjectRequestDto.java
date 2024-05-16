package seniorproject.models.dto.projectRequests;

import lombok.Data;
import seniorproject.models.concretes.enums.ERole;

import java.util.UUID;

@Data
public class UserIdProjectRequestDto {
    UUID sessionId;
    ERole[] roles;
}
