package home.board.web.controller;

import home.board.domain.PostForm;
import home.board.domain.dto.ModifyPostDto;
import home.board.domain.dto.PostDto;
import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import home.board.domain.uploadTest.UploadFile;
import home.board.file.FileStore;
import home.board.web.service.CommentService;
import home.board.web.service.GalleryService;
import home.board.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final GalleryService galleryService;
    private final PostService postService;
    private final CommentService commentService;
    private final FileStore fileStore;

    @PostMapping("/{galleryName}/write")
    public void write(@PathVariable("galleryName") String galleryName, @RequestBody PostDto postDto, HttpServletResponse response) throws IOException {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            ComicGallery comicGallery = new ComicGallery(postDto, galleryId);
            postService.write_ComicGallery(comicGallery);
        } else if (galleryId == 2) {
            ApexGallery apexGallery = new ApexGallery(postDto, galleryId);
            postService.write_ApexGallery(apexGallery);
        }
        String redir = "localhost:8080/" + galleryName;
        response.sendRedirect(redir);
    }

    @PostMapping("/board/new")
    public void newPost(@ModelAttribute PostForm form, HttpServletResponse response) throws IOException {
        long galleryId = Long.parseLong(form.getGalleryId());

        if (galleryId == 1) {
            // 파일을 먼저 저장하고, 거기서 나온 postId를 기반으로 파일테이블을 만들어서 관리
            ComicGallery comicGallery = new ComicGallery(form, galleryId);
            postService.write_ComicGallery(comicGallery);
            List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
                for (UploadFile storeImageFile : storeImageFiles) {
                    String fullPath = fileStore.getFullPath(storeImageFile.getStoreFileName());
                    ComicGalleryFileTable comicGalleryFileTable = new ComicGalleryFileTable(storeImageFile, fullPath, comicGallery);
                    postService.saveFileComicGallery(comicGalleryFileTable);
                }
        } else if (galleryId == 2) {
            ApexGallery apexGallery = new ApexGallery(form, galleryId);
            postService.write_ApexGallery(apexGallery);
        }
        response.sendRedirect(form.getGalleryName() + "/list");
    }


    // 여기로 넘어오기전에 password나 author 검증과정을 통해서 해당유저가 맞는지 확인부터해야함
    // 이전 글 내용을 보여주는건 프론트의 역할같음
    @PostMapping("/{galleryName}/modify/{postId}")
    public void modify(@PathVariable("galleryName") String galleryName, @PathVariable("postId") long postId,
                       @RequestBody ModifyPostDto modifyPostDto, HttpServletResponse response) {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            postService.modify_ComicGallery(postId, modifyPostDto);
        } else if (galleryId == 2) {
            postService.modify_ApexGallery(postId, modifyPostDto);
        }
    }

    @PostMapping("{galleryName}/{postId}/delete")
    public void deletePost(@PathVariable String galleryName, @PathVariable long postId) {
        long galleryId = galleryService.findGalleryByName(galleryName);
        if (galleryId == 1) {
            commentService.deleteAllMangall(postId);
            postService.deleteComicGalleryPost(postId);
        } else if (galleryId == 2) {
            commentService.deleteAllApexgall(postId);
            postService.deleteApexGalleryPost(postId);
        }
    }
}
