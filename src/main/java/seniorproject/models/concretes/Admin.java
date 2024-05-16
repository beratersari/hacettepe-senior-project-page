package seniorproject.models.concretes;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User {
    @MapsId
    @Id
    @JoinColumn(name = "id")
    private UUID id;
}
