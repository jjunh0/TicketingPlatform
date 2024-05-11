package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post readById(Long postId) {
        Post post = em.createQuery("select distinct p from Post p left join fetch p.commentList left join fetch p.member where p.id = :id", Post.class)
                .setParameter("id", postId)
                .getSingleResult();

        return post;
    }

    public List<Post> readByMemberId(String memberId) {
        return em.createQuery("select p from Post p where p.member.memberId like :memberId", Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Post> readAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public Long counts() {
        Long count = em.createQuery("select COUNT(p) from Post p", Long.class)
                .getSingleResult();

        return count;
    }

    public List<Post> readPostPage(int page, int pageCount) {
        List<Post> postList = em.createQuery("select p from Post p", Post.class)
                .setFirstResult(page)
                .setMaxResults(pageCount)
                .getResultList();

        return postList;
    }

    public List<Post> readPostPage(int page, int pageCount, String searchText) {
        List<Post> searchPost = null;

        if(searchText != null) {
            searchPost = em.createQuery("select distinct p from Post p " +
                            "where p.postSubject like :searchSubject or p.postContents like :searchContent", Post.class)
                    .setParameter("searchSubject", "%" + searchText + "%")
                    .setParameter("searchContent", "%" +  searchText + "%")
                    .setFirstResult(page)
                    .setMaxResults(pageCount)
                    .getResultList();
        }

        return searchPost;
    }

    public Long counts(String searchText) {
        Long count = 0L;

        if(searchText != null) {
            count = em.createQuery("select COUNT(distinct p) from Post p " +
                            "where p.postSubject like :searchSubject or p.postContents like :searchContent", Long.class)
                    .setParameter("searchSubject", "%" + searchText + "%")
                    .setParameter("searchContent", "%" + searchText + "%")
                    .getSingleResult();
        }

        return count;
    }

    public Long deletePost(Post post) {
        Long id = post.getId();
        em.remove(post);
        em.flush();
        em.clear();
        return id;
    }

}
