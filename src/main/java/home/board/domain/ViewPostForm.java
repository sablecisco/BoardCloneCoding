package home.board.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ViewPostForm {
//일단 이미지는 뺌 못하겠다
    public String username;

    public String title;
    public String content;

    public ViewPostForm(BasePostEntity baseEntity) {
        this.username = baseEntity.getUserName();
        this.title = baseEntity.getTitle();
        this.content = baseEntity.getContent();
    }

    public ViewPostForm(String userName, String title, String content) {
        this.username = userName;
        this.title = title;
        this.content = content;
    }
}
