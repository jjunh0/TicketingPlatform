package com.culturelife.TicketingPlatform.SecurityConfiguration;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class InitialDataLoader {

    private final MemberRepository memberRepository;

    @Autowired
    public InitialDataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    @Transactional
    public CommandLineRunner loadData() {
        return args -> {
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
            admin.setRoles(Arrays.asList(UserRole.ADMIN));

            Member user = new Member();
            user.setMemberEmail("user@example.com");
            user.setMemberId("user");
            user.setMemberName("Normal User");
            user.setPassword(passwordEncoder.encode("123"));
            user.setUniversityAttendance(true);
            user.setMemberCreateDate(localDateTime);
            user.setMemberUpdateDate(localDateTime);
            user.setRoles(Arrays.asList(UserRole.USER));

            memberRepository.save(admin);
            memberRepository.save(user);
        };
    }
}
