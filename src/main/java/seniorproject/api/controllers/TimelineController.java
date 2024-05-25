package seniorproject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.TimelineService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.concretes.Timeline;
import seniorproject.models.dto.TimelineDto;
import seniorproject.models.dto.TimelineRequestDto;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    @Autowired
    public TimelineController(TimelineService timelineService) {
        super();
        this.timelineService = timelineService;
    }

    @PostMapping("/getTimelinesByProjectTypeId")
    public DataResult<List<TimelineDto>> getTimelinesByProjectTypeId(@RequestBody TimelineRequestDto timelineRequestDto) {
        return timelineService.getByProjectTypeId(timelineRequestDto.getProjectTypeId());
    }
}
