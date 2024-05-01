package com.culturelife.TicketingPlatform.Service;


import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Question;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;

    @Transactional
    public Long createMember(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMemberByMemberId = memberRepository.findByMemberId(member.getMemberId());
        Optional<Member> findMemberByMemberEmail = memberRepository.findByEmail(member.getMemberEmail());
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

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        Member findMember = memberRepository.findId(id);
        validateFindMember(findMember);
        return findMember;
    }

    public Member findMemberByMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        validateFindMember(findMember.orElse(null));
        return findMember.orElse(null);
    }

    public List<Member> findMemberByName(String name) {
        List<Member> findMemberList = memberRepository.findByMemberName(name);
        validateFindMember(findMemberList);
        return findMemberList;
    }

    public Member findMemberByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
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
        Member updateMember = findMemberByMemberId(memberId);
        updateMember = member;
    }

    @Transactional
    public void updateMemberByMemberName(String memberName, Member member) {
        Member updateMember = findMemberByName(memberName).get(0);

        if(updateMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        updateMember = member;
    }


    @Transactional
    public Long deleteQuestion(Long questionId) {
        Question deleteQuestion = questionRepository.findId(questionId);
        Member member = deleteQuestion.getMember();
        deleteQuestion.setMember(null);
        member.getQuestionList().remove(deleteQuestion);

        return deleteQuestion.getId();
    }
}
