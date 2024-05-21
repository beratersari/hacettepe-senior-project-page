package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seniorproject.business.abstracts.DocumentService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.DocumentDto;
import seniorproject.models.dto.UploadDocumentDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/documents")
public class DocumentController {

    DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        super();
        this.documentService = documentService;
    }

    @PostMapping("/uploadDocument")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR', 'ROLE_STUDENT')")
    public DataResult<DocumentDto> uploadDocument(@ModelAttribute UploadDocumentDto file) throws IOException {
        return documentService.uploadDocument(file);
    }

    @PostMapping("/downloadDocument")
    public DataResult<DocumentDto> getDocuments(@RequestBody String documentName) throws IOException {
        return documentService.downloadDocument(documentName);
    }

}
