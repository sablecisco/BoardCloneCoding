package home.board.domain.galleries.apex;

import home.board.domain.BaseTimeEntity;
import home.board.domain.dto.CommentDao;
import home.board.domain.dto.ModifyCommentDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ApexGalleryComments extends BaseTimeEntity {
    @SequenceGenerator(
            name = "APEXGALL_COMMENT_GENERATOR",
            sequenceName = "APEXGALL_COMMENT_SEQ", // 매핑할 데이터베이스 시퀀스 이름
            initialValue = 1,
            allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APEXGALL_COMMENT_GENERATOR")
    @Column(name = "Comment_Id")
    private long id;

    @Column(nullable = false)
    private long post_id;

    @Column(name = "ParentsComment_id")
    private long ParentsComment_id = 0L;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public ApexGalleryComments(long post_id, CommentDao commentDao) {
        this.post_id = post_id;
        this.author = commentDao.getAuthor();
        this.password = commentDao.getPassword();
        this.content = commentDao.getContent();
    }

    public ApexGalleryComments() {

    }

    public void modify(ModifyCommentDto modifyCommentDto) {
        this.author = modifyCommentDto.getAuthor();
        this.password = modifyCommentDto.getPassword();
        this.content = modifyCommentDto.getContent();
    }
}
