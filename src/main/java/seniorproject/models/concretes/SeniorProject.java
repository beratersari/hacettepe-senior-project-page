package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import seniorproject.models.dto.projectTypeRequests.SeniorProjectDto;

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
        seniorProjectDto.setTerm(this.getTerm());
        seniorProjectDto.setActiveness(this.getActiveness());
        seniorProjectDto.setTimelines(this.getTimelines().stream().map(Timeline::getDeliveryName).collect(Collectors.toList()));
        if(this.getProjects() == null)
            return seniorProjectDto;
        seniorProjectDto.setProjects(this.getProjects().stream().map(Project::toProjectDto).collect(Collectors.toList()));
        return seniorProjectDto;
    }

}
