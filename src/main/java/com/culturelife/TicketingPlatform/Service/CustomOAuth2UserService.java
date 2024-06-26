package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes); // 네이버 로그인시 필요

        String email = null;
        String name = null;

        if (registrationId.equals("google")) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
        } else if (registrationId.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            email = (String) response.get("email");
            name = (String) response.get("nickname");

            /*
                 네이버의 경우, 사용자 정보가 response 객체 안에 존재하므로
                 attributes 맵에서 직접 "email" 키를 찾을 수 없음
                 이를 해결하기 위해 네이버의 사용자 정보를,
                 DefaultOAuth2User 객체를 생성하기 전에 attributes 맵에 직접 추가해야 함.

                //  attributes.put("email", email);
                //  attributes.put("name", name);

                 ++ attributes 맵은
                 Collections.unmodifiableMap 으로 래핑된 불변 맵이어서 put이 불가능
                 새로운 Map을 만든 후에 저장하고 해당 Map을
                 attributes 대신 return DefaultOAuth2User()의 파라미터로 넘겨줌
             */
            modifiedAttributes.put("email", email);
            modifiedAttributes.put("name", name);
        }

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        Optional<Member> existingMember = memberRepository.readByEmail(email);
        Member member;

        if (existingMember.isEmpty()) {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
            member = new Member();
            member.setMemberEmail(email);
            member.setMemberName(name != null ? name : "No Name");
            member.setMemberId(email);
            member.setPassword(""); // 소셜 로그인 사용자에게는 비밀번호가 필요하지 않음
            member.setRole(UserRole.USER);
            member.setUniversityAttendance(true);
            member.setMemberCreateDate(localDateTime);
            member.setMemberUpdateDate(localDateTime);

            memberRepository.save(member);
        } else {
            member = existingMember.get();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(member.getRole().getRole()));


        return new DefaultOAuth2User(authorities, modifiedAttributes, "email");
    }
}