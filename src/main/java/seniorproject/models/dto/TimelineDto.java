package seniorproject.models.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TimelineDto {
    private UUID id;
    private Date deliveryDate;
    private String deliveryName;
}
