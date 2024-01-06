package hello.login.web;

import hello.login.web.member.Member;
import hello.login.web.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        // 로그인 X
        if (memberId == null) {
            return "home";
        }

        // 로그인 O
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) { // 로그인 정보 유효하지 않을 경우
            return "home";
        }
        model.addAttribute("member", loginMember); // 로그인 정보 유효할 경우
        return "loginHome";
    }
}