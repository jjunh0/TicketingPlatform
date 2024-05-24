package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createMember(Member member) {
        validateDuplicateMember(member);
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        member.setMemberCreateDate(localDateTime);
        member.setMemberUpdateDate(localDateTime);
        member.setUniversityAttendance(true);

        // 어드민 부분은 수정 예정
        if(member.getMemberId().equals("admin")) {
            member.getRoles().add(UserRole.ADMIN);
        } else {
            member.getRoles().add(UserRole.USER);
        }
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> readMemberByMemberId = memberRepository.readByMemberId(member.getMemberId());
        Optional<Member> readMemberByMemberEmail = memberRepository.readByEmail(member.getMemberEmail());
        if (readMemberByMemberId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 ID의 회원입니다");
        }

        if(readMemberByMemberEmail.isPresent()) {
            throw new IllegalStateException("이미 존재하는 Email의 회원입니다.");
        }
    }

    private void validateReadMember(Member readMember) {
        if(readMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    private void validateReadMember(List<Member> readMemberList) {
        if(readMemberList.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    public List<Member> readAllMembers() {
        return memberRepository.readAll();
    }

    public Member readMemberById(Long id) {
        Member readMember = memberRepository.readById(id);
        validateReadMember(readMember);
        return readMember;
    }

    public Member readMemberByMemberId(String memberId) {
        Optional<Member> readMember = memberRepository.readByMemberId(memberId);
        validateReadMember(readMember.orElse(null));
        return readMember.orElse(null);
    }

    public List<Member> readMemberByName(String name) {
        List<Member> readMemberList = memberRepository.readByMemberName(name);
        validateReadMember(readMemberList);
        return readMemberList;
    }

    public Member readMemberByEmail(String email) {
        Optional<Member> readMember = memberRepository.readByEmail(email);
        validateReadMember(readMember.orElse(null));
        return readMember.orElse(null);
    }

    @Transactional
    public void updateMemberById(Long id, Member member) {
        Member updateMember = readMemberById(id);
        updateMember = member;
    }

    @Transactional
    public void updateMemberByMemberId(String memberId, Member member) {
        Member updateMember = readMemberByMemberId(memberId);
        updateMember = member;
    }

    @Transactional
    public void updateMemberByMemberName(String memberName, Member member) {
        Member updateMember = readMemberByName(memberName).get(0);

        if(updateMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        updateMember = member;
    }


    @Transactional
    public Long deleteQuestion(Long questionId) {
        Post deletePost = postRepository.readById(questionId);
        Member member = deletePost.getMember();
        deletePost.setMember(null);
        member.getPostList().remove(deletePost);

        return deletePost.getId();
    }
}
