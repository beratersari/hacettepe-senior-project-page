package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.AnnouncementDto;

import java.util.List;
import java.util.UUID;

public interface AnnouncementService {
    DataResult<AnnouncementDto> createAnnouncement(AnnouncementDto createAnnouncementDto);

    DataResult<List<AnnouncementDto>> getAnnouncements();

    DataResult<AnnouncementDto> deleteAnnouncement(UUID announcementId);

    DataResult<AnnouncementDto> updateAnnouncement(AnnouncementDto updateAnnouncementDto);
}