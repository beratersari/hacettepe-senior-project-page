package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.AnnouncementService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.dataAccess.abstracts.AnnouncementDao;
import seniorproject.models.concretes.Announcement;
import seniorproject.models.dto.AnnouncementDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnouncementManager implements AnnouncementService {
    AnnouncementDao announcementDao;

    @Autowired
    public AnnouncementManager(AnnouncementDao announcementDao) {
        super();
        this.announcementDao = announcementDao;
    }


    @Override
    public DataResult<AnnouncementDto> createAnnouncement(AnnouncementDto createAnnouncementDto) {
        Announcement announcement = new Announcement();
        announcement.setContent(createAnnouncementDto.getContent());
        announcement.setTitle(createAnnouncementDto.getTitle());
        announcement.setCreatedDate(new Date());
        try {
            announcementDao.save(announcement);
        } catch (Exception e) {
            return new SuccessDataResult<>(null, "An error occurred while creating announcement");
        }
        return new SuccessDataResult<>(announcement.toAnnouncementDto(), "Announcement created successfully");
    }

    @Override
    public DataResult<List<AnnouncementDto>> getAnnouncements() {
        List<Announcement> announcements = announcementDao.findAllByOrderByCreatedDateDesc();
        List<AnnouncementDto> announcementDtos = announcements.stream().map(Announcement::toAnnouncementDto).collect(Collectors.toList());

        return new SuccessDataResult<>(announcementDtos, "Announcements listed successfully");
    }

    @Override
    public DataResult<AnnouncementDto> deleteAnnouncement(UUID announcementId) {
        Announcement announcement = announcementDao.findById(announcementId).orElse(null);
        if (announcement == null) {
            return new SuccessDataResult<>(null, "Announcement not found");
        }
        announcementDao.delete(announcement);
        return new SuccessDataResult<>(announcement.toAnnouncementDto(), "Announcement deleted successfully");
    }

    @Override
    public DataResult<AnnouncementDto> updateAnnouncement(AnnouncementDto updateAnnouncementDto) {
        Announcement announcement = announcementDao.findById(updateAnnouncementDto.getId()).orElse(null);
        if (announcement == null) {
            return new SuccessDataResult<>(null, "Announcement not found");
        }
        announcement.setContent(updateAnnouncementDto.getContent());
        announcement.setTitle(updateAnnouncementDto.getTitle());
        try {
            announcementDao.save(announcement);
        } catch (Exception e) {
            return new SuccessDataResult<>(null, "An error occurred while updating announcement");
        }
        return new SuccessDataResult<>(announcement.toAnnouncementDto(), "Announcement updated successfully");
    }
}
