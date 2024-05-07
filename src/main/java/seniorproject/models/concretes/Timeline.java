package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

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
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name = "project_type_id")
    @JsonBackReference
    private ProjectType projectType;
}
