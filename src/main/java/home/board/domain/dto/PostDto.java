package home.board.domain.dto;

import lombok.Data;

@Data
public class PostDto {

    private String userName;
    private String password;
    private String title;
    private String content;
}

