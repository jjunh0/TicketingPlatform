package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Question;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {

    private final EntityManager em;

    public void save(Question question) {
        em.persist(question);
    }

    public Question findId(Long id) {
        return em.find(Question.class, id);
    }

    public List<Question> findByMemberId(String memberId) {
        return em.createQuery("select q from Question q where q.member.memberId like :memberId", Question.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class)
                .getResultList();
    }

    public Long deleteQuestion(Question question) {
        Long id = question.getId();
        em.remove(question);
        return id;
    }

}
