package home.board.web.config;

import home.board.domain.Member;
import home.board.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Member member = createMember(userRequest, oAuth2User);

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

    private Member createMember(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oauth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email  = oauth2User.getAttribute("email");
        String nickname = oauth2User.getAttribute("name");
        String password = bCryptPasswordEncoder.encode("CommonPassword");

        Member member = memberRepository.findByUserName(username);
        if (member == null) {
            member = Member.builder()
                    .username(username)
                    .provider(provider)
                    .providerId(providerId)
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .build();
            memberRepository.save(member);
        }
        return member;
    }
}
