package home.board.domain.dto;

import lombok.Data;


@Data
public class CommentDao {

    private String author;
    private String password;

    private String content;

}
