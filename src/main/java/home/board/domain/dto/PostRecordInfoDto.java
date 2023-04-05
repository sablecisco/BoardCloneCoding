package home.board.domain.dto;

import home.board.domain.Member;
import lombok.Getter;

@Getter
public class PostRecordInfoDto {

    private String userName;
    private String nickName;

    public PostRecordInfoDto(Member member) {
        this.userName = member.getUserName();
        this.nickName = member.getNickName();
    }
}
