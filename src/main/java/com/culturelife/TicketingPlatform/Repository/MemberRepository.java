package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Optional<Member> findByMemberId(String memberId) {
        List<Member> resultList = em.createQuery("select m from Member m where m.memberId like :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();

        if (!resultList.isEmpty()) {
            return Optional.of(resultList.get(0));
        } else {
            return Optional.empty();
        }
    }

    public List<Member> findByMemberName(String memberName) {
        return em.createQuery("select m from Member m where m.memberName like :memberName", Member.class)
                .setParameter("memberName", memberName)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findByEmail(String memberEmail) {
        List<Member> resultList = em.createQuery("select m from Member m where m.memberEmail like :memberEmail", Member.class)
                .setParameter("memberEmail", memberEmail)
                .getResultList();

        if (!resultList.isEmpty()) {
            return Optional.of(resultList.get(0));
        } else {
            return Optional.empty();
        }
    }


}
