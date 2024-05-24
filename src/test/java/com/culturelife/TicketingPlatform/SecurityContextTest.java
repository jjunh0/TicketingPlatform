package com.culturelife.TicketingPlatform;

import com.culturelife.TicketingPlatform.Controller.RoleCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class SecurityContextTest {

    @Autowired
    private RoleCheckController roleCheckController;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCheckRolesAsAdmin() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleCheckController).build();

        mockMvc.perform(get("/checkRoles"))
                .andDo(result -> {
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
                        } else if (principal.toString().equals("anonymousUser")) {
                            System.out.println("principal is not UserDetails type");
                        } else {
                            System.out.println("Unexpected principal type: " + principal.getClass().getName());
                        }
                    }
                });
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCheckRolesAsUser() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleCheckController).build();

        mockMvc.perform(get("/checkRoles"))
                .andDo(result -> {
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
                        } else if (principal.toString().equals("anonymousUser")) {
                            System.out.println("principal is not UserDetails type");
                        } else {
                            System.out.println("Unexpected principal type: " + principal.getClass().getName());
                        }
                    }
                });
    }
}
