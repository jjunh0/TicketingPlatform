package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Controller
@SessionAttributes("name")
public class SignInController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signinup")
    public String gotoLoginPage(Model model) {
        model.addAttribute("member", new Member());
        return "signinup";
    }

    @PostMapping("/registerMember")
    public String registerUser(Member member) {
        ResponseEntity response = null;
        try {

            if(!(member.getPassword().equals(member.getRepeatPassword()))) {
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

    @GetMapping("/signup")
    public String gotoSignUpPage() {
        return "sign_up";
    }
}
