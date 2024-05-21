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

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentManager implements DocumentService {

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

        Document document = documentDao.findByDocumentName(file.getProjectId() + "_" + file.getTimelineId());
        if (document == null) {
            document = new Document();
        }
        MultipartFile multipartFile = file.getFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new ErrorDataResult<>("File is empty or null");
        }

        Optional<Project> projectOptional = projectDao.findById(file.getProjectId());
        if (!projectOptional.isPresent()) {
            return new ErrorDataResult<>("Project not found");
        }

        Optional<Timeline> timelineOptional = timelineDao.findById(file.getTimelineId());
        if (!timelineOptional.isPresent()) {
            return new ErrorDataResult<>("Timeline not found");
        }

        Project project = projectOptional.get();
        Timeline timeline = timelineOptional.get();

        // Update document properties
        document.setData(multipartFile.getBytes());
        document.setProject(project);
        document.setTimeline(timeline);
        document.setSize(multipartFile.getSize());
        document.setUploadDate(new Date());


        // Save or update the document
        documentDao.save(document);

        return new SuccessDataResult<>(document.toDocumentDto(), "Document uploaded successfully");
    }

    @Override
    public DataResult<DocumentDto> downloadDocument(String documentName) {
        Document document;
        try {
            document = documentDao.findByDocumentName(StringUtils.stripFrontBack(documentName, "\"", "\""));
        }
        catch (Exception e) {
            return new ErrorDataResult<>("Document not found");
        }


        if (document == null) {
            return new ErrorDataResult<>("Document not found");
        }

        return new SuccessDataResult<>(document.toDocumentDto(), "Document downloaded successfully");
    }
}
