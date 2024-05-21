package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Entity.dto.postFormDTO;
import com.culturelife.TicketingPlatform.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {
  private final PostService postService;
  // 질의 응답 게시판 글 목록으로 이동
  @GetMapping("community")
  public String communityListsController(){
    return "community";
  }
  // 질의 응답 글 작성 페이지로 이동
  @GetMapping("create_post")
  public String communityWriteController(){
    return "createpost";
  }
  // 질의 응답 게시판의 작성된 글 페이지 보기
  // 이는 나중에 수정해야함
  @GetMapping("view_poster/{postId}")
  public String communityViewController(@PathVariable("postId") Long postId, Model model){
    Post post = postService.readPostById(postId);
    model.addAttribute("post", post);
    model.addAttribute("comments", post.getCommentList());
    return "post";
  }

  @PostMapping("create_newpost")
  public void CreatePost(postFormDTO postForm) {
    Post post = new Post();
    post.setPostSubject(postForm.getPostSubject());
    post.setPostContents(postForm.getPostContents());
    postService.createPost("1", post);
  }

}
