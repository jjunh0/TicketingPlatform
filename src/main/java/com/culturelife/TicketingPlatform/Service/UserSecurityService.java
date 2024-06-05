package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException{

        // 입력받은 사용자 명으로 _siteMember 변수를 가진 Optinal타입의 객체 생성
        Optional<Member> _siteMember = this.memberRepository.readByMemberId(memberId);
        // 만약 사용자가 없다면 에러 생성
        if(_siteMember.isEmpty()) {
            System.out.println("User not found: " + memberId);
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // Optional<Member>타입을 Member 엔티티 타입으로 형변환
        Member siteMember = _siteMember.get();
        System.out.println("User found: " + siteMember.getMemberId());

        // 사용자 정보를 담을 객체 생성
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserRole role = siteMember.getRole();
        System.out.println("Role: " + role.getRole());
        authorities.add(new SimpleGrantedAuthority(siteMember.getRole().getRole()));

        System.out.println("User loaded: " + siteMember.getMemberId());
        return new User(siteMember.getMemberId(), siteMember.getPassword(), authorities);
    }
}
