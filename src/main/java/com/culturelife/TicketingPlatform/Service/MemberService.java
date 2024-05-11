package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMemberByMemberId = memberRepository.readByMemberId(member.getMemberId());
        Optional<Member> findMemberByMemberEmail = memberRepository.readByEmail(member.getMemberEmail());
        if (findMemberByMemberId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 ID의 회원입니다");
        }

        if(findMemberByMemberEmail.isPresent()) {
            throw new IllegalStateException("이미 존재하는 Email의 회원입니다.");
        }
    }

    private void validateFindMember(Member findMember) {
        if(findMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    private void validateFindMember(List<Member> findMemberList) {
        if(findMemberList.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    public List<Member> readAllMembers() {
        return memberRepository.readAll();
    }

    public Member findMemberById(Long id) {
        Member findMember = memberRepository.readById(id);
        validateFindMember(findMember);
        return findMember;
    }

    public Member readMemberByMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.readByMemberId(memberId);
        validateFindMember(findMember.orElse(null));
        return findMember.orElse(null);
    }

    public List<Member> readMemberByName(String name) {
        List<Member> findMemberList = memberRepository.readByMemberName(name);
        validateFindMember(findMemberList);
        return findMemberList;
    }

    public Member readMemberByEmail(String email) {
        Optional<Member> findMember = memberRepository.readByEmail(email);
        validateFindMember(findMember.orElse(null));
        return findMember.orElse(null);
    }

    @Transactional
    public void updateMemberById(Long id, Member member) {
        Member updateMember = findMemberById(id);
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
