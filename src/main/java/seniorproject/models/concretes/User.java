package seniorproject.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "roles"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Size(min = 6,message = "Username length must be minimum 6")
    @Column(name = "username",unique = true)
    private String username;

    @Email(message = "Email Should Be Valid")
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Size(min = 8,message = "Password length must be minimum 8")
    @Column(name = "password")
    private String password;


    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            )
    )
    private Set<Role> roles  = new HashSet<>();


    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}