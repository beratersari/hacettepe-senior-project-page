package seniorproject.models.concretes;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
public class Document {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private Timeline projectInTimeline;

}
