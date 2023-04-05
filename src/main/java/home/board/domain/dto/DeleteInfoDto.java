package home.board.domain.dto;

import lombok.Data;

@Data
public class DeleteInfoDto {

    private Long gallery_id;
    private String postId;
    private String password;
    private String nickname;
}
