package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment reaById(Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        return comment;
    }

    public List<Comment> readAll(Long postId) {
        List<Comment> commentList = em.createQuery("select c from Comment c where c.post.id = :id", Comment.class)
                .setParameter("id", postId)
                .getResultList();

        return commentList;
    }

    public void delete(Comment comment) {
        em.remove(comment);
        em.flush();
        em.clear();
    }

    public void deleteAll(Long postId) {
        em.createQuery("delete from Comment c where c.post.id = :id")
                .setParameter("id", postId)
                .executeUpdate();
        em.clear();
    }

    public List<Comment> search(String searchText) {
        List<Comment> searchComment = null;
        searchComment = em.createQuery("select c from Comment c where c.commentContents like :searchText", Comment.class)
                .setParameter("searchText", "%" + searchText + "%")
                .getResultList();

        return searchComment;
    }

    public Long counts(Long postId) {
        Long count = em.createQuery("SELECT COUNT(c) FROM Post p LEFT JOIN FETCH p.commentList c WHERE p.id = :postId", Long.class)
                .setParameter("postId", postId)
                .getSingleResult();

        return count;
    }

}