package seniorproject.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seniorproject.business.abstracts.UrlAndImagesService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.models.dto.UrlAndImagesDto;
import seniorproject.models.dto.UrlAndImagesRequest;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/urlAndImages")
public class UrlAndImagesController {

    UrlAndImagesService urlAndImagesService;

    @Autowired
    public UrlAndImagesController(UrlAndImagesService urlAndImagesService) {
        super();
        this.urlAndImagesService = urlAndImagesService;
    }

    @PostMapping("/addUrlAndImages")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResult<UrlAndImagesDto> addUrl(@ModelAttribute UrlAndImagesRequest urlAndImagesDto) throws IOException {
        return urlAndImagesService.addUrlAndImages(urlAndImagesDto);
    }

    @PostMapping("/getUrlAndImages")
    public DataResult<List<UrlAndImagesDto>> getUrlAndImages() throws IOException {
        return urlAndImagesService.getUrlAndImages();
    }
}
