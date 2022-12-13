package home.board.domain.dto;

import lombok.Data;

@Data
public class ModifyPostDto {

    private String author;
    private String password;
    private String title;
    private String content;

}
