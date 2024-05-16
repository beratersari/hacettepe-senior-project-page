package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import seniorproject.models.dto.TimelineDto;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "timelines")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groups"})
public class Timeline {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private String deliveryName;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name = "project_type_id")
    @JsonBackReference
    private ProjectType projectType;

    public TimelineDto toTimelineDto() {
        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setId(this.id);
        timelineDto.setDeliveryName(this.deliveryName);
        timelineDto.setDeliveryDate(this.deliveryDate);
        return timelineDto;
    }
}
