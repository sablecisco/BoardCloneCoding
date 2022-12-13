package home.board.web.controller;

import home.board.domain.PostForm;

import home.board.domain.ViewPostForm;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import home.board.domain.uploadTest.FileTable;
import home.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostRepository postRepository;

    @GetMapping("/{galleryName}/new")
    public String getPostForm(@ModelAttribute PostForm form, @PathVariable String galleryName) {
        System.out.println("내프로젝트의 루트경로는?  " + System.getProperty("user.dir"));
        return "forms/" + galleryName + "Form";
    }

    @GetMapping("/{galleryName}/view/{postId}")
    public String getViewFromDB(@PathVariable String galleryName, @PathVariable long postId, Model model) {
        if (galleryName.equals("comic")) {
            ComicGallery comicGallery = postRepository.findByComicGalleryPostId(postId);
            ViewPostForm viewPostForm = new ViewPostForm(comicGallery);
            model.addAttribute("viewPostForm", viewPostForm);
            List<ComicGalleryFileTable> fileTableList = postRepository.findComicGalleryFileTableByPost(comicGallery);

            model.addAttribute("fileTableList", fileTableList);
        }

        return "viewForms/" + galleryName + "ViewForm";
    }
}
