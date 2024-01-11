package hello.login.web;

import hello.login.web.argumentresolver.Login;
import hello.login.web.member.Member;
import hello.login.web.member.MemberRepository;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
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

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        // 로그인 X
        if (member == null) {
            return "home";
        }

        // 로그인 O
        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        // 로그인 X
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 유효하지 않은 회원
        if (loginMember == null) {
            return "home";
        }
        // 로그인 O
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model) {
        // 로그인 X
        if (loginMember == null) {
            return "home";
        }
        // 로그인 O
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
        // 로그인 X
        if (loginMember == null) {
            return "home";
        }
        // 로그인 O
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}