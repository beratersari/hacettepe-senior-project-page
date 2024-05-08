package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import seniorproject.models.concretes.Keyword;

import java.security.Key;
import java.util.Optional;
import java.util.UUID;

public interface KeywordDao extends JpaRepository<Keyword, UUID> {
    Optional<Keyword> findByName(String name);
}
