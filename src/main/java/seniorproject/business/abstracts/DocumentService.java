package seniorproject.business.abstracts;

import org.springframework.web.multipart.MultipartFile;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.DocumentDto;
import seniorproject.models.dto.UploadDocumentDto;

import java.io.IOException;
import java.util.UUID;

public interface DocumentService {
    DataResult<DocumentDto> uploadDocument(UploadDocumentDto file) throws IOException;

    DataResult<DocumentDto> downloadDocument(String documentId) throws IOException;

    DataResult<DocumentDto> addGradeDocument(DocumentDto documentDto);
}
