package seniorproject.models.concretes;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admins")
public class Admin extends UserEntity {
    @MapsId
    @JoinColumn(name = "user_id")
    @Id
    private long id;
}
