package seniorproject.models.concretes;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;
import seniorproject.models.concretes.enums.ERole;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(generator = "uuid2")
    @JsonProperty("id")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column
    private ERole name;

}
