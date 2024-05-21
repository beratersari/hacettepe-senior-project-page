package seniorproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seniorproject.models.concretes.Document;

import java.util.Optional;
import java.util.UUID;

public interface DocumentDao extends JpaRepository<Document, UUID> {

    @Query("SELECT d FROM Document d WHERE d.documentName = :documentName")
    Document findByDocumentName(@Param("documentName") String documentName);
}
