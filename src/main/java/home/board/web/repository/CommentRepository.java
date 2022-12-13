package home.board.web.repository;

import home.board.domain.galleries.apex.ApexGalleryComments;
import home.board.domain.galleries.comic.ComicGalleryComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save_ComicGallery_Comments(ComicGalleryComments comicGalleryComments) {
        em.persist(comicGalleryComments);
    }

    public void save_ApexGallery_Comments(ApexGalleryComments apexGalleryComments) {
        em.persist(apexGalleryComments);
    }

    public ComicGalleryComments findMangallCommnetByCommnetId(long commentId) {
        return em.createQuery("select m from ComicGalleryComments m where m.id = :commentId", ComicGalleryComments.class)
                .setParameter("commentId", commentId)
                .getSingleResult();
    }

    public ApexGalleryComments findApexGallCommentByCommentId(long commentId) {
        return em.createQuery("select m from ApexGalleryComments m where m.id = :commentId", ApexGalleryComments.class)
                .setParameter("commentId", commentId)
                .getSingleResult();
    }

    public void deleteComicGalleryComments(long postId, long commentId) {
        em.createQuery("delete from ComicGalleryComments m where m.post_id = :postId and m.id = :commentId")
                .setParameter("postId", postId)
                .setParameter("commentId", commentId)
                .executeUpdate();

    }

    public void deleteApexGalleryCommnets(long postId, long commentId) {
        em.createQuery("delete from ApexGalleryComments m where m.post_id = :postId and m.id = :commentId")
                .setParameter("postId", postId)
                .setParameter("commentId", commentId)
                .executeUpdate();

    }

    public void deleteAllComicGallery(long postId) {
        em.createQuery("delete from ComicGalleryComments m where m.post_id = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }

    public void deleteAllApexGallery(long postId) {
        em.createQuery("delete from ApexGalleryComments m where m.post_id = :postId")
                .setParameter("postId", postId)
                .executeUpdate();
    }
}
