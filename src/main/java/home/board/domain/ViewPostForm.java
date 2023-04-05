package home.board.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ViewPostForm {
//일단 이미지는 뺌 못하겠다
    public String nickName;

    private String title;
    private String content;
    private boolean loginFlag = false;
    private String password;
    private String ipAddr;
    private Long postId;

    public ViewPostForm(BasePostEntity baseEntity, long postId) {
        this.nickName = baseEntity.getNickName();
        this.title = baseEntity.getTitle();
        this.content = baseEntity.getContent();
        this.password = baseEntity.getPassword();
        if (baseEntity.getPassword() == null) this.loginFlag = true;
        this.ipAddr = baseEntity.getIpAddr();
        this.postId = postId;
    }

    public ViewPostForm(String userName, String title, String content) {
        this.nickName = userName;
        this.title = title;
        this.content = content;
    }
}
