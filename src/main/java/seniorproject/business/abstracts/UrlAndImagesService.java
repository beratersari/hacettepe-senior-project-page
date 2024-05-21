package seniorproject.business.abstracts;

import seniorproject.core.utilities.results.DataResult;
import seniorproject.core.utilities.results.SuccessDataResult;
import seniorproject.models.dto.UrlAndImagesDto;
import seniorproject.models.dto.UrlAndImagesRequest;

import java.io.IOException;
import java.util.List;

public interface UrlAndImagesService {
    DataResult<UrlAndImagesDto> addUrlAndImages(UrlAndImagesRequest urlAndImagesDto) throws IOException;

    DataResult<List<UrlAndImagesDto>> getUrlAndImages();
}
