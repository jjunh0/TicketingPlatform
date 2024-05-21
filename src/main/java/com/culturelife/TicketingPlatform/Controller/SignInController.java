package com.culturelife.TicketingPlatform.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class SignInController {

//    private AuthenticationService authenticationService;
//
//    public SignInController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

    @GetMapping("/sign_in")
    public String gotoLoginPage() {

        return "signinup";
    }

    @GetMapping("/sign_up")
    public String gotoSignUpPage() {
        return "sign_up";
    }
}
