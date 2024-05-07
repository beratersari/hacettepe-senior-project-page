package seniorproject.models.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "keyword_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    public Keyword(String keyword) {
        this.name = keyword;
    }
}
