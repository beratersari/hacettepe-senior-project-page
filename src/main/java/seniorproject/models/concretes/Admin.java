package seniorproject.models.concretes;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User {
    @MapsId
    @Id
    @JoinColumn(name = "id")
    private long id;
}
