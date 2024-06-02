package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Service.AuthenticatedUserService;
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

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public SignInController(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test1")
    @ResponseBody
    public String adminTest() {
        String currentUserMemberId = authenticatedUserService.getCurrentMemberId();
        String roles = authenticatedUserService.getCurrentMemberRoles();

        return "ADMIN 권한 사용자만 접근 가능한 페이지입니다.<br>"
                + authenticatedUserService.getCurrentMemberInfo();
    }

    @GetMapping("/test2")
    @ResponseBody
    public String userTest() {
        String currentUserMemberId = authenticatedUserService.getCurrentMemberId();
        String roles = authenticatedUserService.getCurrentMemberRoles();

        return "ADMIN, USER 권한 사용자만 접근 가능한 페이지입니다.<br>"
                + authenticatedUserService.getCurrentMemberInfo();
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

    /*
        /login에 대한 POST 요청은 SecurityConfig와 UserSecurityService가 처리
     */
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
