package home.board.web.controller;

import home.board.domain.dto.CommentDao;
import home.board.domain.dto.ModifyCommentDto;

import home.board.domain.galleries.apex.ApexGalleryComments;
import home.board.domain.galleries.comic.ComicGalleryComments;
import home.board.web.service.CommentService;
import home.board.web.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final GalleryService galleryService;
    private final CommentService commentService;

    @PostMapping("/{galleryName}/{postId}/comment")
    public void writeComment(@RequestBody CommentDao commentDao,
                             @PathVariable("galleryName") String galleryName, @PathVariable("postId") long postId,
                             HttpServletResponse response, HttpServletRequest request) throws IOException {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if(galleryId == 1) {
            ComicGalleryComments comicGalleryComments = new ComicGalleryComments(postId, commentDao);
            commentService.ComicGallery_comment_save(comicGalleryComments);
        } else if (galleryId == 2) {
            ApexGalleryComments apexGalleryComments = new ApexGalleryComments(postId, commentDao);
            commentService.ApexGallery_comment_save(apexGalleryComments);
        }
        String resURL = "";
        response.sendRedirect(resURL);
    }

    @PostMapping("/{galleryName}/modify/{commentId}/comment")
    public void modify(@PathVariable("galleryName") String galleryName, @PathVariable("commentId") long commentId,
                       @RequestBody ModifyCommentDto modifyCommentDto, HttpServletResponse response) {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            commentService.modify_mangall(commentId, modifyCommentDto);
        } else if (galleryId == 2) {
            commentService.modify_apexgall(commentId, modifyCommentDto);
        }
    }

    @PostMapping("{galleryName}/{postId}/{commentId}/delete")
    public void deleteComment(@PathVariable("galleryName") String galleryName, @PathVariable("postId") long postId,
                           @PathVariable("commentId") long commentId, HttpServletResponse response) {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            commentService.deleteByMangallCommentId(postId, commentId);
        } else if (galleryId == 2) {
            commentService.deleteByApexgallCommentId(postId, commentId);
        }
    }

    // 게시글이 삭제되었을때 작동
    // 게시글에 딸려있던 모든 댓글 삭제
    @PostMapping("{galleryName}/{postId}/deleteAllComment")
    public void deleteCommentWhenPostDeleted(@PathVariable("galleryName") String galleryName,
                                             @PathVariable("postId") long postId, HttpServletResponse response) {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            commentService.deleteAllMangall(postId);
        } else if (galleryId == 2) {
            commentService.deleteAllApexgall(postId);
        }
    }


}
