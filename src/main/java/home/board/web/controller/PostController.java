package home.board.web.controller;

import home.board.domain.PostForm;
import home.board.domain.ViewPostForm;
import home.board.domain.dto.DeleteInfoDto;
import home.board.domain.dto.ModifyPostDto;
import home.board.domain.dto.PostDto;
import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.apex.ApexGalleryFileTable;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import home.board.domain.uploadTest.UploadFile;
import home.board.file.FileStore;
import home.board.web.service.CommentService;
import home.board.web.service.GalleryService;
import home.board.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Enumeration;
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

    @PostMapping("/new") // 왜 여기있는 주소 + redir 주소가 되는거지?
    public void newPost(@ModelAttribute PostForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        long galleryId = Long.parseLong(form.getGalleryId());
        String remoteAddr = request.getRemoteAddr();
        form.setIpAddr(remoteAddr);
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
            List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
            for (UploadFile storeImageFile : storeImageFiles) {
                String fullPath = fileStore.getFullPath(storeImageFile.getStoreFileName());
                ApexGalleryFileTable apexGalleryFileTable = new ApexGalleryFileTable(storeImageFile, fullPath, apexGallery);
                postService.saveFileApexGallery(apexGalleryFileTable);
            }
        }
        response.sendRedirect(form.getGalleryName() + "/list?page=1");
    }

    @GetMapping ("/{galleryName}/delete/{postId}") // 공용 삭제 처리기
    public void deleteConfirm(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable String galleryName, @PathVariable long postId) throws IOException {
        if (galleryName.equals("comic")) {
            postService.deleteComicGalleryPost(postId);
        }
        response.sendRedirect("/");
    }

    @GetMapping("/{galleryName}/userCheck/{order}/{postId}") // 로그인 유저 삭제 처리기
    public void deleteConfirm(Authentication authentication, HttpServletRequest request, HttpServletResponse response,
                                        Model model, @PathVariable String galleryName, @PathVariable long postId, @PathVariable String order) throws IOException {
        ComicGallery comicGalleryPost = postService.findComicGalleryPost(postId);

        if (order.equals("delete")) {
            if (authentication.getName().equals(comicGalleryPost.getUserName())) {
                model.addAttribute("postId", postId);
                response.sendRedirect("/DeleteConfirm/" + postId);
            }
            // 아닌경우 오류처리 (사실 아예 안뜨게 하는게 베스트)
            // 아마 내 글 리스트를 구현하면 해당 글 접속할 때 내 글인지 판별하고 아예 이 과정을 지워도 될 거같음 (보안상 더 좋음)
        }
        if (order.equals("modify")) {
            if (authentication.getName().equals(comicGalleryPost.getUserName())) {
                model.addAttribute("postId", postId);
                response.sendRedirect("/ModifyConfirm/" + postId); // 수정하는건 컨펌 없이 바로 수정 가능한게 좋을듯
            }
        }

    }

    @PostMapping("{galleryName}/ConfirmPasswordCheck/{postId}") // 비밀번호 체크 하고 삭제처리기로 보냄
    // 나중에 글 수정도 이 프로세스로 진행 할 건데, 아마 request에서 이전 url을 기주으로 판단하면 될 것 같음
    public void passwordCheck(@RequestParam String password, HttpServletRequest request ,HttpServletResponse response, @PathVariable long postId,@PathVariable String galleryName) throws IOException {
        ComicGallery comicGalleryPost = postService.findComicGalleryPost(postId);
        request.getRequestURL();
        if (comicGalleryPost.getPassword().equals(password)) {
            response.sendRedirect("/" + galleryName + "/delete/" + postId);
        }
        // 오류처리 필요
    }

//    @GetMapping("/DeleteConfirm")
//    public ResponseEntity<?> deleteConfirm() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/LoginDeleteConfirmPage"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//    }


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
