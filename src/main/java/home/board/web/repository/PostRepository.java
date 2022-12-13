package home.board.web.repository;

import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.apex.ApexGalleryFileTable;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public ComicGallery findByComicGalleryPostId(long postId) {
        return em.createQuery("select m from ComicGallery m where m.id = :postId", ComicGallery.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    public ApexGallery findByApexGalleryPostId(long postId) {
        return em.createQuery("select a from ApexGallery a where a.id = :postId", ApexGallery.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    public void save_ComicGallery(ComicGallery comicGallery) {
        em.persist(comicGallery);
    }

    public void save_ApexGallery(ApexGallery apexGallery) {
        em.persist(apexGallery);
    }

    public void deleteComicGalleryPost(long postId) {
        em.createQuery("delete from ComicGallery m where m.id = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }

    public void deleteApexgallPost(long postId) {
        em.createQuery("delete from ApexGallery m where m.id = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }

    public void saveComicGalleryFileTable(ComicGalleryFileTable fileTable) {
        em.persist(fileTable);
    }

    public List<ComicGalleryFileTable> findComicGalleryFileTableByPost(ComicGallery comicGallery) {
        return em.createQuery("select c from ComicGalleryFileTable c where c.comicGallery = :comicGallery", ComicGalleryFileTable.class)
                .setParameter("comicGallery", comicGallery)
                .getResultList();
    }

    public void saveApexGalleryFileTable(ApexGalleryFileTable fileTable) {
        em.persist(fileTable);
    }
}
