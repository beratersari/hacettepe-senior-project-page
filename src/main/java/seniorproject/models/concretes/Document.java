package seniorproject.models.concretes;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import seniorproject.models.dto.DocumentDto;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "timeline_id")
    private Timeline timeline;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Long size;
    private Date uploadDate;

    @Column(name = "grade")
    private String grade;

    @Column(nullable = false, unique = true)
    private String documentName;

    public DocumentDto toDocumentDto() {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(this.id);
        documentDto.setDeliveryDate(this.timeline.getDeliveryDate());
        documentDto.setDeliveryName(this.timeline.getDeliveryName());
        documentDto.setProjectId(this.project.getId());
        documentDto.setGrade(this.grade);
        documentDto.setDocumentName(this.documentName);
        return documentDto;
    }

}
