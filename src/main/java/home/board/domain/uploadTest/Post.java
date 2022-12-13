package home.board.domain.uploadTest;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Post {

    @Id @GeneratedValue
    private long id;

    private String username;

    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
