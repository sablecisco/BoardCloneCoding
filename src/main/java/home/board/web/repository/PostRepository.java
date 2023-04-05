package home.board.web.repository;

import home.board.domain.dto.GalleryCommonModel;
import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.apex.ApexGalleryFileTable;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
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

    public void deleteApexgalleryPost(long postId) {
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

    public List<ApexGalleryFileTable> findApexGalleryFileTableByPost(ApexGallery apexGallery) {
        return em.createQuery("select c from ApexGalleryFileTable c where c.apexGallery = :apexGallery", ApexGalleryFileTable.class)
                .setParameter("apexGallery", apexGallery)
                .getResultList();
    }

    public void saveApexGalleryFileTable(ApexGalleryFileTable fileTable) {
        em.persist(fileTable);
    }

    public List<GalleryCommonModel> GalleryPaging(int offset, int limit, String galleryName) {
        return em.createQuery("select new home.board.domain.dto.GalleryCommonModel(m.id, m.title, m.nickName) from " + galleryName + " m order by m.createdDate DESC")
                .setFirstResult(offset * limit)
                .setMaxResults(limit)
                .getResultList();
    }
}
