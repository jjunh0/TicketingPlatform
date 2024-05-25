package com.culturelife.TicketingPlatform.SecurityConfiguration;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Service.UserSecurityService;
import com.culturelife.TicketingPlatform.filter.CheckAlreadyLoggedInFilter;
import com.culturelife.TicketingPlatform.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

    @Autowired
    public SecurityConfig(UserSecurityService userSecurityService) { // 수정된 부분
        this.userSecurityService = userSecurityService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userSecurityService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.securityContext((context) -> context
                        .requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/h2-console/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // 프레임 옵션을 비활성화하여 H2 콘솔에 접근할 수 있도록 설정
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                // CsrfCookieFilter를 기본 인증 필터 이후에 추가
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests)->requests

                        .requestMatchers("/css/**", "/js/**", "/images/**", "/icons/**").permitAll()
                        /*
                            ADMIN 권한 사용자만 접근 가능한 페이지를 설정할 경우
                            해당 경로를 바로 아래 코드 부분에 requestMatchers("/signinup", "/home").hasRole("ADMIN") 형태로 추가해주시면 됩니다.
                         */
                        .requestMatchers("/test1").hasRole("ADMIN")

                        /*
                            ADMIN 권한, USER 권한 사용자가 접근 가능한 페이지는
                            같은 방식으로 하단의 requestMatchers 메서드의 파라미터에 추가합니다.
                         */
                        .requestMatchers("/test2").hasAnyRole("ADMIN", "USER")
                        /*
                            ADMIN, USER 권한을 포함하여 로그인하지 않은 사용자까지 접근 가능한 페이지는
                            같은 방식으로 하단의 requestMatchers 메서드의 파라미터에 추가합니다.
                            모든 페이지에 대해 접근을 허용하는 "/**"를 파라미터로 받고 있지만,
                            이 주석의 위쪽에 위치한 requestMatchers에 파라미터로 넣어준 경로가 있을 경우
                            위쪽 라인에 먼저 선언된 requestMatchers의 권한 설정이 우선됩니다.
                         */
                        .requestMatchers("/", "/home", "/**").permitAll())
                .addFilterBefore(new CheckAlreadyLoggedInFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin((formLogin) -> formLogin
                        .loginPage("/signinup")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/signinup?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userSecurityService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
}
