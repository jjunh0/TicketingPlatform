package com.culturelife.TicketingPlatform.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
@RestController
public class RoleCheckController {

    @GetMapping("/checkRoles")
    public String checkRoles() {
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                System.out.println("Username: " + userDetails.getUsername());
                System.out.println("Authorities: ");

                for (GrantedAuthority authority : authorities) {
                    System.out.println(authority.getAuthority());
                }
            }else if(principal.toString().equals("anonymousUser")){
                System.out.println("principal is not UserDetails type");
            } else {
                System.out.println(2222);
            }
        }

        return "Roles checked and printed in console";
    }
}
