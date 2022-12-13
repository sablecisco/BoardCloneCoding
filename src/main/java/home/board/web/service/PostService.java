package home.board.web.service;

import home.board.domain.dto.ModifyPostDto;
import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import home.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write_ComicGallery(ComicGallery comicGallery) {
        postRepository.save_ComicGallery(comicGallery);
    }

    public void write_ApexGallery(ApexGallery apexGallery) {
        postRepository.save_ApexGallery(apexGallery);
    }

    public void modify_ComicGallery(long postId, ModifyPostDto modifyPostDto) {
        ComicGallery comicGallery = postRepository.findByComicGalleryPostId(postId);
        comicGallery.modify(modifyPostDto);
    }

    public void modify_ApexGallery(long postId, ModifyPostDto modifyPostDto) {
        ApexGallery apexGallery = postRepository.findByApexGalleryPostId(postId);
        apexGallery.modify(modifyPostDto);
    }

    public void deleteComicGalleryPost(long postId) {
        postRepository.deleteComicGalleryPost(postId);
    }

    public void deleteApexGalleryPost(long postId) {
        postRepository.deleteApexgallPost(postId);
    }

    public void saveFileComicGallery(ComicGalleryFileTable comicGalleryFileTable) {
        postRepository.saveComicGalleryFileTable(comicGalleryFileTable);
    }
}
