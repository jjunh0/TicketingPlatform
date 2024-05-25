package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatedUserService {

    private final MemberRepository memberRepository;

    public AuthenticatedUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
        현재 로그인 된 멤버의 아이디(고유값이 아닌, 로그인 할 때 기입하는 아이디)를 가져옴
     */
    public String getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return "알 수 없음";
    }

    /*
        현재 로그인된 유저의 id(고유값)를 가져옴
     */
    public Long getCurrentId() {
        Optional<Member> memberOpt = getCurrentMember();
        if (memberOpt.isPresent()) {
            return memberOpt.get().getId();
        }
        return null; // 로그인된 사용자가 없는 경우 null 반환
    }

    /*
        현재 로그인된 유저의 권한을 가져옴
     */
    public String getCurrentMemberRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().toString();
        }
        return "알 수 없음";
    }

    /*
        현재 로그인된 유저의 member 객체를 가져옴
     */
    public Optional<Member> getCurrentMember() {
        String memberId = getCurrentMemberId();
        return memberRepository.readByMemberId(memberId);
    }

    /*
        /test1, /test2 에 사용
     */
    public String getCurrentMemberInfo() {
        Optional<Member> memberOpt = getCurrentMember();
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            return  "ID: " + getCurrentId() + "<br>" +
                    "MemberId: " + member.getMemberId() + "<br>" +
                    "MemberEmail: " + member.getMemberEmail() + "<br>" +
                    "MemberName: " + member.getMemberName() + "<br>" +
                    "University Attendance: " + member.isUniversityAttendance() + "<br>" +
                    "Roles: " + getCurrentMemberRoles();
        }
        return "알 수 없음";
    }


}
