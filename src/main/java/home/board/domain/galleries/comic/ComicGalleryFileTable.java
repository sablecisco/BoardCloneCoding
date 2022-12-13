package home.board.domain.galleries.comic;

import home.board.domain.BaseTimeEntity;
import home.board.domain.uploadTest.UploadFile;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class ComicGalleryFileTable extends BaseTimeEntity {

    @Id @GeneratedValue
    private long id;

    private String fileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private ComicGallery comicGallery;

    public ComicGalleryFileTable(UploadFile file, String fullPath, ComicGallery post) {
        this.fileName = file.getStoreFileName();
        this.filePath = fullPath;
        this.comicGallery = post;
    }
}
