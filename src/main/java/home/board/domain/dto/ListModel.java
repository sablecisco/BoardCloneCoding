package home.board.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ListModel {

    private String PostName;
    private long PostID;
    private String PostUserName;

    public ListModel(String postName, long postID, String postUserName) {
        PostName = postName;
        PostID = postID;
        PostUserName = postUserName;
    }
}
