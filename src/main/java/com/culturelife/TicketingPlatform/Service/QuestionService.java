package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Question;
import com.culturelife.TicketingPlatform.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    @Transactional
    public Long createPost(String memberId, Question question) {
        Member member = memberService.findMemberByMemberId(memberId);
        member.getQuestionList().add(question);
        question.setMember(member);

        questionRepository.save(question);
        return question.getId();
    }

    public List<Question> findAllMembers() {
        return questionRepository.findAll();
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findQuestionByMemberId(String memberId) {
        return questionRepository.findByMemberId(memberId);
    }

}
