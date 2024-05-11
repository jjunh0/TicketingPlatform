package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Transactional
    public Long createPost(String memberId, Post post) {
        Member member = memberService.findMemberByMemberId(memberId);
        member.getPostList().add(post);
        post.setMember(member);

        postRepository.save(post);
        return post.getId();
    }

    public Page<Post> searchPostPage(int page, String search) {
        Long searchPostTotalCount = postRepository.counts(search);
        int startPage = 0;
        int pageCount = 10;

        startPage = (int) (searchPostTotalCount - page * 10);
        if(startPage < 0) {
            pageCount = pageCount + startPage;
            startPage = 0;
        }

        List<Post> postList = postRepository.readPostPage(startPage, pageCount, search);
        Collections.reverse(postList);

        Pageable pageable = PageRequest.of(page-1 ,10);
        PageImpl<Post> postPage = new PageImpl<>(postList, pageable, searchPostTotalCount);
        return postPage;
    }


    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findPostByMemberId(String memberId) {
        return postRepository.findByMemberId(memberId);
    }

}
