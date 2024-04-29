package seniorproject.models.concretes;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keywords")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
}
