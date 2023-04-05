package home.board.web.repository;

import home.board.domain.galleries.comic.ComicGallery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryRepository {

    private final EntityManager em;


}
