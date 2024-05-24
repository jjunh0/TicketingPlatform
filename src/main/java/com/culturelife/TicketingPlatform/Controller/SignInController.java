package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@SessionAttributes("name")
public class SignInController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @GetMapping("/signinup")
//    public String gotoLoginPage(Model model) {
//        model.addAttribute("member", new Member());
//        return "signinup";
//    }

    @GetMapping("/test1")
    // 리스폰스바디 애너테이션
    // hello 메소드의 응답 결과 반환
    @ResponseBody
    public String hell1o() {
        return "test 1번!";
    }

    @GetMapping("/test2")
    // 리스폰스바디 애너테이션
    // hello 메소드의 응답 결과 반환
    @ResponseBody
    public String hello2() {
        return "test 2번!";
    }

    @PostMapping("/registerMember")
    public String registerUser(Member member) {
        ResponseEntity response = null;
        try {

            if(!(member.getPassword()
                    .equals(member.getRepeatPassword()))) {
                throw new IllegalStateException("비밀번호 확인란 입력과 일치하지 않습니다.");
            }

            String hashPwd = passwordEncoder.encode(member.getPassword());
            member.setPassword(hashPwd);
            memberService.createMember(member);

            return "redirect:/home";

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("exception 발생 원인 : " + ex.getMessage());
            ex.printStackTrace();
            return "signinup";
        }
    }


//    @PostMapping("/login")
//    public String gotoSignUpPage(@RequestParam String username,
//                                 @RequestParam String password,
//                                 Model model,
//                                 HttpSession session) {
//        Optional<Member> optionalMember = memberRepository.readByMemberId(username);
//
//        if(optionalMember.isPresent()) {
//            Member member = optionalMember.get();
//
//            if(passwordEncoder.matches(password, member.getPassword())) {
//                session.setAttribute("loggedInUser", member);
//                return "redirect:/home";
//            } else {
//                model.addAttribute("loginError", "잘못된 비밀번호입니다.");
//                return "signinup";
//            }
//        }  else {
//            model.addAttribute("loginError", "존재하지 않는 아이디입니다.");
//            return "signinup";
//        }
//    }
}
