package home.board.web.controller;

import home.board.domain.PostForm;
import home.board.domain.uploadTest.FileTable;
import home.board.domain.uploadTest.Post;
import home.board.domain.uploadTest.UploadFile;
import home.board.file.FileStore;
import home.board.web.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

//@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UploadRepository uploadRepository;
    private final FileStore fileStore;

    @GetMapping("/posts/new")
    public String newPost(@ModelAttribute PostForm form) {
        return "postForm";
    }

    @GetMapping("/board/new")
    public String newPostForm(@ModelAttribute PostForm form) {
        return "post-form";
    }

//    @GetMapping("/write/new")
//    public String newItem(@ModelAttribute PostForm form) {
//        System.out.println("UploadController.newItem");
//        return "write";
//    }

    @PostMapping("/posts/new")
    public String saveItem(@ModelAttribute PostForm form) throws IOException {
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Post post = new Post();
        post.setUsername(form.getUserName());
        post.setContent(form.getContent());
        uploadRepository.savePost(post);

        for (UploadFile uploadFile : storeImageFiles) {
            FileTable fileTable = new FileTable();
            String fileName = uploadFile.getUploadFileName();
            fileTable.setFileName(fileName);
            fileTable.setFilePath(fileStore.getFullPath(fileName));
            fileTable.setPost(post);
            uploadRepository.saveFile(fileTable);
        }
        return "redirect:/postForm";
    }

    @PostMapping("/board/new")
    public String writeOnBoard(@ModelAttribute PostForm form) throws IOException {
        System.out.println("UploadController.writeOnBoard");
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
        for (UploadFile storeImageFile : storeImageFiles) {
            System.out.println("storeImageFile.getStoreFileName() = " + storeImageFile.getStoreFileName());
        }

        Post post = new Post();
        post.setUsername(form.getUserName());
        post.setContent(form.getContent());
        uploadRepository.savePost(post);

        for (UploadFile uploadFile : storeImageFiles) {
            FileTable fileTable = new FileTable();
            String fileName = uploadFile.getUploadFileName();
            fileTable.setFileName(fileName);
            fileTable.setFilePath(fileStore.getFullPath(fileName));
            fileTable.setPost(post);
            uploadRepository.saveFile(fileTable);
        }
        return "redirect:/postForm";
    }

}
