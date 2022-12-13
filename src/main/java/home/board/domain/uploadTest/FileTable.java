package home.board.domain.uploadTest;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class FileTable {

    @Id @GeneratedValue
    private long id;

    private String fileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public FileTable(UploadFile file, String fullPath, Post post) {
        this.fileName = file.getStoreFileName();
        this.filePath = fullPath;
        this.post = post;
    }
}
