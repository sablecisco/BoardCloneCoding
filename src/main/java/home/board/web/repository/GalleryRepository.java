package home.board.web.repository;

import home.board.domain.GalleryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<GalleryInfo, Long> {

    GalleryInfo findByGalleryName(String galleryName);
}
