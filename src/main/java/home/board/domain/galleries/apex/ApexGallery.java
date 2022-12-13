package home.board.domain.galleries.apex;

import home.board.domain.PostForm;
import home.board.domain.dto.ModifyPostDto;
import home.board.domain.dto.PostDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ApexGallery {
    @SequenceGenerator(
            name = "apexgall_SEQ_GENERATOR",
            sequenceName = "APEXGALL_SEQ", // 매핑할 데이터베이스 시퀀스 이름
            initialValue = 1,
            allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apexgall_SEQ_GENERATOR")
    @Column(name = "Post_Id")
    private long id;

    @Column(nullable = false)
    private long gallery_id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    public void setId(long id) {
        this.id = id;
    }

    public ApexGallery() {

    }

    public ApexGallery(PostDto postDto, long gallery_id)  {
        this.gallery_id = gallery_id;
        this.author = postDto.getUserName();
        this.password = postDto.getPassword();
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
    }

    public void modify(ModifyPostDto modifyPostDto)  {
        this.author = modifyPostDto.getAuthor();
        this.password = modifyPostDto.getPassword();
        this.title = modifyPostDto.getTitle();
        this.content = modifyPostDto.getContent();
    }

    public ApexGallery(PostForm postForm, long gallery_id) {
        this.gallery_id = gallery_id;
        this.author = postForm.getUserName();
        this.password = postForm.getPassword();
        this.title = postForm.getTitle();
        this.content = postForm.getContent();
    }
}
