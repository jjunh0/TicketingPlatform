package com.culturelife.TicketingPlatform.Controller;

import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "sign_in", method = RequestMethod.GET)
    public String gotoLoginPage() {

        return "sign_in";
    }

    @RequestMapping(value = "sign_up", method = RequestMethod.GET)
    public String gotoSignUpPage() {
        return "sign_up";
    }
}
