package home.board.domain.galleries.apex;

import home.board.domain.BaseTimeEntity;
import home.board.domain.uploadTest.UploadFile;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class ApexGalleryFileTable extends BaseTimeEntity {

    @Id @GeneratedValue
    private long id;

    private String fileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private ApexGallery apexGallery;

    public ApexGalleryFileTable(UploadFile uploadFile, String filePath, ApexGallery apexGallery) {
        this.fileName = uploadFile.getStoreFileName();
        this.filePath = filePath;
        this.apexGallery = apexGallery;
    }
}
