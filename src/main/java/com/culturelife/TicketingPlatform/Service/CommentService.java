package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Repository.CommentRepository;
import com.culturelife.TicketingPlatform.Repository.MemberRepository;
import com.culturelife.TicketingPlatform.Repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public void createComment(String memberId, Long postId, String content) {
        Optional<Member> returnMember = memberRepository.findByMemberId(memberId);
        Member member = returnMember.orElse(null);
        Post post = postRepository.findById(postId);

        if(content.isEmpty() == false) {
            Comment comment = new Comment();
            comment.setCommentContents(content);
            comment.setMember(member);
            comment.setPost(post);
            member.getCommentList().add(comment);
            post.getCommentList().add(comment);
            commentRepository.save(comment);
        }
    }

    public List<Comment> readCommentList(Long postId) {
        List<Comment> commentList = commentRepository.findAll(postId);

        return commentList;
    }

    public void updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId);
        comment.setCommentContents(content);
    }
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId);
        Comment comment = post.getCommentList().get((int) (commentId - 1));
        commentRepository.delete(comment);
        post.getCommentList().remove((int)(commentId-1));
    }

    public void deleteCommentAll(Long postId) {
        Post post = postRepository.findById(postId);
        post.getCommentList().clear();
        commentRepository.deleteAll(postId);
    }

    public List<Comment> searchCommentList(String searchText) {
        List<Comment> searchCommentList = commentRepository.search(searchText);

        return searchCommentList;
    }
}