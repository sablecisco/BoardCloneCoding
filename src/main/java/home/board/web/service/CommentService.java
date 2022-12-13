package home.board.web.service;

import home.board.domain.dto.ModifyCommentDto;
import home.board.domain.galleries.apex.ApexGalleryComments;
import home.board.domain.galleries.comic.ComicGalleryComments;
import home.board.web.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void ComicGallery_comment_save(ComicGalleryComments comicGalleryComments) {
        commentRepository.save_ComicGallery_Comments(comicGalleryComments);
    }

    public void ApexGallery_comment_save(ApexGalleryComments apexGalleryComments) {
        commentRepository.save_ApexGallery_Comments(apexGalleryComments);
    }

    public void modify_mangall(long commentId, ModifyCommentDto modifyCommentDto) {
        ComicGalleryComments comicGalleryComments = commentRepository.findMangallCommnetByCommnetId(commentId);
        comicGalleryComments.modify(modifyCommentDto);
    }

    public void modify_apexgall(long commentId, ModifyCommentDto modifyCommentDto) {
        ApexGalleryComments apexGalleryComments = commentRepository.findApexGallCommentByCommentId(commentId);
        apexGalleryComments.modify(modifyCommentDto);
    }

    public void deleteByMangallCommentId(long postId, long commentId) {
        commentRepository.deleteComicGalleryComments(postId, commentId);
    }

    public void deleteByApexgallCommentId(long postId, long commentId) {
        commentRepository.deleteApexGalleryCommnets(postId, commentId);
    }


    public void deleteAllMangall(long postId) {
        commentRepository.deleteAllComicGallery(postId);
    }

    public void deleteAllApexgall(long postId) {
        commentRepository.deleteAllApexGallery(postId);
    }
}
