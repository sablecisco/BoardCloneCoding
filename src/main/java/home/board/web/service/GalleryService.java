package home.board.web.service;

import home.board.domain.GalleryInfo;
import home.board.web.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public long findGalleryByName(String galleryName) {
        GalleryInfo galleryInfo = galleryRepository.findByGalleryName(galleryName);
        return galleryInfo.getGallery_id();
    }
}
