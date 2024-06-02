package com.culturelife.TicketingPlatform;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class InitialDataLoader {

    private final MemberService memberService;
    private final ConcertSeatService concertSeatServiceq;
    private final PerformanceService performanceService;
    private final PostService postService;
    private final CommentService commentService;
    private final TicketService ticketService;
    private final ConcertSeatService concertSeatService;
    private final SeatService seatService;

    @Bean
    @Transactional
    public CommandLineRunner loadData() {
        return args -> {
            InitialCreateMember();
            InitialPerformance();
        };
    }

    private void InitialCreateMember() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);

        Member admin = new Member();
        admin.setMemberEmail("admin@example.com");
        admin.setMemberId("admin");
        admin.setMemberName("Admin User");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setUniversityAttendance(true);
        admin.setMemberCreateDate(localDateTime);
        admin.setMemberUpdateDate(localDateTime);
        admin.setRole(UserRole.ADMIN);

        Member user = new Member();
        user.setMemberEmail("user@example.com");
        user.setMemberId("user");
        user.setMemberName("Normal User");
        user.setPassword(passwordEncoder.encode("123"));
        user.setUniversityAttendance(true);
        user.setMemberCreateDate(localDateTime);
        user.setMemberUpdateDate(localDateTime);
        user.setRole(UserRole.USER);

        memberService.createMember(admin);
        memberService.createMember(user);

        for(int i=1;i<=5;i++) {
            Member test = new Member();
            String testEmail = "test" + i + "@example.com";
            test.setMemberEmail(testEmail);
            test.setMemberId("test" + i);
            test.setMemberName("테스트" + i);
            test.setPassword(passwordEncoder.encode("123"));
            test.setUniversityAttendance(true);
            test.setMemberCreateDate(localDateTime);
            test.setMemberUpdateDate(localDateTime);
            test.setRole(UserRole.USER);
            memberService.createMember(test);
        }
    }

    private void InitialPerformance() {

        for(int i=1;i<=29;i++) {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);

            Performance testPerformance = new Performance();
            testPerformance.setPerformanceName("테스트용 공연 " + i);
            testPerformance.setPerformanceCount(50);
            testPerformance.setPerformanceContents("테스트용 공연 내용 " + i);
            testPerformance.setPerformancePrice(100000);
            testPerformance.setPerformanceDate(localDateTime);

            performanceService.createPerformance(testPerformance);
        }
    }
}
