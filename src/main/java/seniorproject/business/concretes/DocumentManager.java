package seniorproject.business.concretes;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import seniorproject.business.abstracts.DocumentService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.ErrorDataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.DocumentDao;
import seniorproject.dataAccess.abstracts.ProjectDao;
import seniorproject.dataAccess.abstracts.TimelineDao;
import seniorproject.models.concretes.Document;
import seniorproject.models.concretes.Project;
import seniorproject.models.concretes.Timeline;
import seniorproject.models.dto.DocumentDto;
import seniorproject.models.dto.UploadDocumentDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentManager implements DocumentService {

    private String documentPath = "../hacettepe-senior-project-page/src/main/java/seniorproject/resources/documents";
    private final DocumentDao documentDao;
    private final ProjectDao projectDao;
    private final TimelineDao timelineDao;

    @Autowired
    public DocumentManager(DocumentDao documentDao, ProjectDao projectDao, TimelineDao timelineDao) {
        this.documentDao = documentDao;
        this.projectDao = projectDao;
        this.timelineDao = timelineDao;
    }

    @Override
    public DataResult<DocumentDto> uploadDocument(UploadDocumentDto file) throws IOException {
        // Check if the file is null or empty
        MultipartFile multipartFile = file.getFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new ErrorDataResult<>("File is empty or null");
        }

        // Ensure the file is a PDF
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            return new ErrorDataResult<>("File is not a PDF");
        }

        // Check if the project exists
        Optional<Project> projectOptional = projectDao.findById(file.getProjectId());
        if (!projectOptional.isPresent()) {
            return new ErrorDataResult<>("Project not found");
        }

        // Check if the timeline exists
        Optional<Timeline> timelineOptional = timelineDao.findById(file.getTimelineId());
        if (!timelineOptional.isPresent()) {
            return new ErrorDataResult<>("Timeline not found");
        }

        Project project = projectOptional.get();
        Timeline timeline = timelineOptional.get();

        // Construct the document name
        String documentName = file.getProjectId() + "_" + file.getTimelineId();

        // Find or create a new document
        Document document = documentDao.findByDocumentName(documentName);
        if (document == null) {
            document = new Document();
        }

        // Create the document path
        String documentPath = this.documentPath + File.separator + project.getId() + File.separator + timeline.getId();
        File directory = new File(documentPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return new ErrorDataResult<>("Failed to create directory for the document");
            }
        }

        // Save the PDF file to the filesystem
        File documentFile = new File(documentPath + File.separator + documentName + ".pdf");
        try (OutputStream os = Files.newOutputStream(documentFile.toPath())) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            return new ErrorDataResult<>("Failed to save file: " + e.getMessage());
        }

        // Update document properties
        document.setDocumentName(documentName);
        document.setProject(project);
        document.setTimeline(timeline);
        document.setSize(multipartFile.getSize());
        document.setUploadDate(new Date());

        // Save or update the document in the database
        documentDao.save(document);

        return new SuccessDataResult<>(document.toDocumentDto(), "Document uploaded successfully");
    }


    @Override
    public DataResult<DocumentDto> downloadDocument(String documentName) throws IOException {
        // Strip quotes from the document name if present
        documentName = StringUtils.stripFrontBack(documentName, "\"", "\"");

        // Find the document in the database
        Document document = documentDao.findByDocumentName(documentName);
        if (document == null) {
            Document newDocument = new Document();
            newDocument.setDocumentName(documentName);
            newDocument.setTimeline(timelineDao.findById(UUID.fromString(documentName.split("_")[1])).orElse(null));
            newDocument.setProject(projectDao.findById(UUID.fromString(documentName.split("_")[0])).orElse(null));
            documentDao.save(newDocument);
            return new SuccessDataResult<>(newDocument.toDocumentDto(), "Document not found", newDocument.getGrade());
        }
        // Construct the document path
        String documentPath = this.documentPath + File.separator + document.getProject().getId() + File.separator + document.getTimeline().getId();
        File documentFile = new File(documentPath + File.separator + documentName + ".pdf");

        if (!documentFile.exists()) {
            return new ErrorDataResult<>(document.toDocumentDto(),"Document file not found",  document.getGrade());
        }

        // Read the file content
        byte[] fileContent = Files.readAllBytes(documentFile.toPath());

        // Convert the document to DTO and set the file content
        DocumentDto documentDto = document.toDocumentDto();
        documentDto.setFile(fileContent);

        return new SuccessDataResult<>(documentDto, "Document downloaded successfully");
    }


    @Override
    public DataResult<DocumentDto> addGradeDocument(DocumentDto documentDto) {
        Document document = documentDao.findByDocumentName(documentDto.getDocumentName());
        if (document == null) {
            Document newDocument = new Document();
            newDocument.setDocumentName(documentDto.getDocumentName());
            newDocument.setGrade(documentDto.getGrade());

            Optional<Project> projectOptional = projectDao.findById(documentDto.getProjectId());
            if (!projectOptional.isPresent()) {
                return new ErrorDataResult<>("Project not found");
            }

            newDocument.setProject(projectOptional.get());
            Optional<Timeline> timelineOptional = timelineDao.findById(documentDto.getTimelineId());
            if (!timelineOptional.isPresent()) {
                return new ErrorDataResult<>("Timeline not found");
            }
            newDocument.setTimeline(timelineOptional.get());
            documentDao.save(newDocument);
            return new SuccessDataResult<>(newDocument.toDocumentDto(), "Grade added successfully");
        }

        document.setGrade(documentDto.getGrade());
        documentDao.save(document);

        return new SuccessDataResult<>(document.toDocumentDto(), "Grade added successfully");
    }
}
