package home.board.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostForm {
    private String userName;
    private String nickName;
    private String ipAddr;
    private String password;
    private String title;
    private String content;

    private List<MultipartFile> imageFiles;

    private String galleryName;
    private String galleryId;
}