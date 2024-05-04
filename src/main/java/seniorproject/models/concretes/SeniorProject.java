package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import seniorproject.models.dto.ProjectDto;
import seniorproject.models.dto.SeniorProjectDto;

import javax.persistence.*;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "senior_projects")
public class SeniorProject extends ProjectType{

    @Column(unique = true)
    @JsonFormat(pattern = "yyyy-yyyy")
    private String term;

    public SeniorProjectDto toSeniorProjectDto() {
        SeniorProjectDto seniorProjectDto = new SeniorProjectDto();
        seniorProjectDto.setId(this.getId());
        seniorProjectDto.setName(this.getName());
        seniorProjectDto.setActiveness(this.getActiveness());
        seniorProjectDto.setTimelines(this.getTimelines().stream().map(Timeline::getDeliveryName).collect(Collectors.toList()));
        seniorProjectDto.setProjects(this.getProjects().stream().map(Project::toProjectDto).collect(Collectors.toList()));
        return seniorProjectDto;
    }

}
