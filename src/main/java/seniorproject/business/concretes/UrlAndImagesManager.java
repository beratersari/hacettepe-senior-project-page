package seniorproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seniorproject.business.abstracts.UrlAndImagesService;
import seniorproject.core.utilities.results.DataResult;
import seniorproject.dataAccess.abstracts.UrlAndImagesDao;
import seniorproject.models.concretes.UrlAndImages;
import seniorproject.models.dto.UrlAndImagesDto;
import seniorproject.models.dto.UrlAndImagesRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UrlAndImagesManager implements UrlAndImagesService {

    UrlAndImagesDao urlAndImagesDao;

    @Autowired
    public UrlAndImagesManager(UrlAndImagesDao urlAndImagesDao) {
        super();
        this.urlAndImagesDao = urlAndImagesDao;
    }


    @Override
    public DataResult<UrlAndImagesDto> addUrlAndImages(UrlAndImagesRequest urlAndImagesDto) throws IOException {
        UrlAndImages urlAndImages = new UrlAndImages();

        urlAndImages.setUrl(urlAndImagesDto.getUrl());
        urlAndImages.setImage(urlAndImagesDto.getImage().getBytes());

        urlAndImagesDao.save(urlAndImages);

        return new DataResult<>(urlAndImages.toUrlAndImagesDto(), true);

    }

    @Override
    public DataResult<List<UrlAndImagesDto>> getUrlAndImages() {
        List<UrlAndImages> urlAndImages = urlAndImagesDao.findAll();

        List<UrlAndImagesDto> urlAndImagesDtos = new ArrayList<>();

        for (UrlAndImages urlAndImage : urlAndImages) {
            urlAndImagesDtos.add(urlAndImage.toUrlAndImagesDto());
        }

        return new DataResult<>(urlAndImagesDtos, true);

    }


}
