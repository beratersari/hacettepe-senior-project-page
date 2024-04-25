package seniorproject.models.concretes;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User {
    @MapsId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JoinColumn(name = "id")
    private long id;
}
