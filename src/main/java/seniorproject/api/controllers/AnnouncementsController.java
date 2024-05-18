package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.AnnouncementService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.AnnouncementDto;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/announcements")
public class AnnouncementsController {

    AnnouncementService announcementService;

   @Autowired
    public AnnouncementsController(AnnouncementService announcementService) {
        super();
        this.announcementService = announcementService;
    }

    @PostMapping("/createAnnouncement")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<AnnouncementDto> createAnnouncement(@RequestBody AnnouncementDto createAnnouncementDto) {
        return announcementService.createAnnouncement(createAnnouncementDto);
    }

    @PostMapping("/getAnnouncements")
    public DataResult<List<AnnouncementDto>> getAnnouncements() {
        return announcementService.getAnnouncements();
    }
    @PostMapping("/deleteAnnouncement")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<AnnouncementDto> deleteAnnouncement(@RequestBody UUID announcementId) {
        return announcementService.deleteAnnouncement(announcementId);
    }
    @PostMapping("/updateAnnouncement")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public DataResult<AnnouncementDto> updateAnnouncement(@RequestBody AnnouncementDto updateAnnouncementDto) {
        return announcementService.updateAnnouncement(updateAnnouncementDto);
    }

}
