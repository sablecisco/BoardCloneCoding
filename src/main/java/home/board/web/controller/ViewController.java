package home.board.web.controller;

import home.board.domain.Member;
import home.board.domain.PostForm;

import home.board.domain.ViewPostForm;
import home.board.domain.dto.GalleryCommonModel;
import home.board.domain.dto.ListModel;
import home.board.domain.dto.PostRecordInfoDto;
import home.board.domain.dto.SignUpDto;
import home.board.domain.galleries.apex.ApexGallery;
import home.board.domain.galleries.apex.ApexGalleryFileTable;
import home.board.domain.galleries.comic.ComicGallery;
import home.board.domain.galleries.comic.ComicGalleryFileTable;
import home.board.web.repository.MemberRepository;
import home.board.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/{galleryName}/new")
    public String getPostForm(Authentication authentication, @ModelAttribute PostForm form, @PathVariable String galleryName,
                              Model model, HttpServletRequest request) {
        if (authentication == null) {
            return "writeForms/" + galleryName + "Form";
        }
        Member member = memberRepository.findByUserName(authentication.getName());
        PostRecordInfoDto postRecordInfoDto = new PostRecordInfoDto(member);

        model.addAttribute("postRecordInfoDto", postRecordInfoDto);
        return "writeForms/" + galleryName + "LoginForm";
    }

    @GetMapping("/{galleryName}/{postId}")
    public String getViewFromDB(@PathVariable String galleryName, @PathVariable long postId, Model model) {
        // 합쳐서 처리하자니 나중에 게시판 분할 소요도 있고 좀 복잡함 (가독성 저하)
        // 근데 놔두자니 이쪽도 이쪽대로 가독성이 심각

        if (galleryName.equals("comic")) {
            ComicGallery comicGallery = postRepository.findByComicGalleryPostId(postId); // service 거치게 변경 할 것
            ViewPostForm viewPostForm = new ViewPostForm(comicGallery, postId); // 이미지 없음
            String password = viewPostForm.getPassword();
            model.addAttribute("viewPostForm", viewPostForm);
            List<ComicGalleryFileTable> fileTableList = postRepository.findComicGalleryFileTableByPost(comicGallery);

            model.addAttribute("fileTableList", fileTableList);
        }

        if (galleryName.equals("apex")) {
            ApexGallery apexGallery = postRepository.findByApexGalleryPostId(postId);
            ViewPostForm viewPostForm = new ViewPostForm(apexGallery, postId);
            model.addAttribute("viewPostForm", viewPostForm);
            List<ApexGalleryFileTable> fileTableList = postRepository.findApexGalleryFileTableByPost(apexGallery);

            model.addAttribute("fileTableList", fileTableList);
        }

        return "viewForms/" + galleryName + "ViewForm";

        //이미지가 안보임
    }


    @GetMapping("/{galleryName}/list")
    public String getPostList(@PathVariable String galleryName, @RequestParam("page") int page, Model model) {
        //galleryName으로 galleryId 찾는 기능 만들어서 그걸로 찾아서 조회
        String gName = changeToUpperCase(galleryName);
        List<GalleryCommonModel> GalleryPostList = postRepository.GalleryPaging(page -1, 5, gName);
        List<ListModel> modelList = new ArrayList<>();

        for (GalleryCommonModel galleryCommonModel : GalleryPostList) {
            ListModel lm = new ListModel(galleryCommonModel.getTitle(), galleryCommonModel.getId(), galleryCommonModel.getNickName());
            modelList.add(lm);
        }

        model.addAttribute("list", modelList);
        return "viewForms/" + gName + "ListForm";
        // 리스트 받아서 이걸 가공해서 뷰로 보여주기?
    }

    private static String changeToUpperCase(String galleryName) {
        galleryName = galleryName.substring(0,1).toUpperCase() + galleryName.substring(1, galleryName.length());
        String gName = galleryName + "Gallery";
        return gName;
    }
}
