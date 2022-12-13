package home.board.domain.dto;

import lombok.Data;

@Data
public class ModifyCommentDto {

    private String author;
    private String password;

    private String content;
}
