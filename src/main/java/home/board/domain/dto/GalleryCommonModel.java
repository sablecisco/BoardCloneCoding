package home.board.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GalleryCommonModel {
    // 기본적인 각 게시글의 포맷을 들고 있음 이를 통해서 하나의 컨트롤러로 다양한 게시판의 정보를 끌고 올 수 있음
    private long id;

    private String title;

    private String nickName;

    public GalleryCommonModel(long id, String title, String nickName) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
    }
}

