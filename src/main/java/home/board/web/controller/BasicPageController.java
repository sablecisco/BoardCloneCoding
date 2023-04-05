package home.board.web.controller;

import home.board.domain.Member;
import home.board.domain.dto.SignUpDto;
import home.board.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class BasicPageController extends BCryptPasswordEncoder {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"/"})
    public String homePage(Authentication authentication) {
        if (authentication != null) {
            return "loginHome";
        }
        return "home";
    }

    @GetMapping("/signUp")
    public String join(Model model) {
        model.addAttribute("SignUpDto", new SignUpDto());
        return "register";
    }

    @PostMapping("/signUp")
    public void singUpComp(HttpServletRequest request, HttpServletResponse response, SignUpDto signUpDto) throws IOException {
        signUpDto.setPassword(bCryptPasswordEncoder.encode(signUpDto.getPassword()));
        Member member = new Member(signUpDto);
        memberRepository.save(member);
        response.sendRedirect("/loginHome");
    }

    @GetMapping("/DeleteConfirm/{postId}") // Modify 랑 통합 할 방법이 있을까
    public String DeletePageConfirm(@PathVariable String postId, Model model) {
        model.addAttribute("postId", postId);
        return "/LoginDeleteConfirmPage";
    }

    @GetMapping("/{galleryName}/passwordCheck/{postId}") // 비밀번호 확인 페이지로 연결해줌
    public String getPasswordCheckPage(Model model, @PathVariable String galleryName, @PathVariable Long postId) {
        model.addAttribute("postId", postId);
        return "/PasswordCheck";
    }
}
