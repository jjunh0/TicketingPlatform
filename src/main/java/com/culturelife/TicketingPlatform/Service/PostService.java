package com.culturelife.TicketingPlatform.Service;

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
        Member member = memberService.readMemberByMemberId(memberId);
        member.getPostList().add(post);
        post.setMember(member);

        postRepository.save(post);
        return post.getId();
    }

    public Page<Post> readPostPage(int page) {
        Long total = postRepository.counts();
        int startPage = 0;
        int remainPageCount = 10;
        int postCount = 10;

        startPage = (int) (total - page * 10);
        if(startPage < 0) {
            remainPageCount = remainPageCount + startPage;
            startPage = 0;
        }

        List<Post> postList = postRepository.readPostPage(startPage, remainPageCount);
        Collections.reverse(postList);

        Pageable pageable = PageRequest.of(page-1 ,postCount);
        PageImpl<Post> postPage = new PageImpl<>(postList, pageable, total);
        return postPage;
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


    public List<Post> readAllPosts() {
        return postRepository.readAll();
    }

    public Post readPostById(Long id) {
        postRepository.updateViewCount(id);
        return postRepository.readById(id);
    }

    public List<Post> readPostByMemberId(String memberId) {
        return postRepository.readByMemberId(memberId);
    }

    @Transactional
    public Long deletePost(Long postId) {
        Post deletePost = postRepository.readById(postId);
        Member member = deletePost.getMember();
        deletePost.setMember(null);
        member.getPostList().remove(deletePost);

        return deletePost.getId();
    }
}
