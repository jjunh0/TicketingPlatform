package com.culturelife.TicketingPlatform.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CheckAlreadyLoggedInFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String) &&
                request.getRequestURI().equals("/signinup")) {
            /*
                이미 로그인한 사용자가 "/signinup"에 접근하면, /home으로 redirect
             */
            response.sendRedirect("/home");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
