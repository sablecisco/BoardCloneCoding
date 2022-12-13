package home.board.web.repository;

import home.board.domain.uploadTest.FileTable;
import home.board.domain.uploadTest.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
public class UploadRepository {

    private final EntityManager em;

    public void savePost(Post post) {
        em.persist(post);

    }

    public void saveFile(FileTable fileTable) {
        em.persist(fileTable);
    }
}
