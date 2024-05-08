package com.culturelife.TicketingPlatform.Repository;

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

    public Question findById(Long questionId) {
        return em.find(Question.class, questionId);
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
        em.flush();
        em.clear();
        return id;
    }

}
