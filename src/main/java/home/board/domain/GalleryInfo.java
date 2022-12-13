package home.board.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class GalleryInfo {

    @Id
    @Column(name = "Gallery_Id")
    private long gallery_id;

    @Column(nullable = false)
    private String galleryName;

}
