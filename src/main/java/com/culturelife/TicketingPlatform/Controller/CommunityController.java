package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Entity.dto.PostDTO;
import com.culturelife.TicketingPlatform.Service.AuthenticatedUserService;
import com.culturelife.TicketingPlatform.Service.MemberService;
import com.culturelife.TicketingPlatform.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommunityController {
  private final PostService postService;
  private final MemberService memberService;
  private final AuthenticatedUserService userService;

  // 요청한 페이지의 글 목록을 model에 추가하여 이동
  @GetMapping("/community/{pageNumber}")
  public String communityPageController(@PathVariable("pageNumber") int pageNumber, Model model){
    Page<Post> postList = postService.readPostPage(pageNumber);
    model.addAttribute("Postlist", postList);
    return "community";
  }
  @GetMapping("/createpost")
  public String communityCreatePost(Model model) {
    if (!userService.getCurrentMember().isPresent()) {
      System.out.println("비회원자는 게시글을 작성할 수 없습니다.");
      return "redirect:/community/1";
    }
    PostDTO postDTO = new PostDTO();
    model.addAttribute("post", postDTO);
    return "createpost";
  }

  @PostMapping("/board")
  public String createPostController(PostDTO postDTO) {
    Post post = new Post();
    post.setPostSubject(postDTO.getPostSubject());
    post.setPostContents(postDTO.getPostContents());
    post.setPostCreateDate(LocalDateTime.now());
    post.setPostUpdateDate(LocalDateTime.now());
    post.setCommentList(new ArrayList<Comment>());
    Member currentMember;
    if(userService.getCurrentMember().isPresent()) currentMember = userService.getCurrentMember().get();
    else{
      System.out.println("유효하지않은 접근");
      return "redirect:/community/1";
    }
    postService.createPost(currentMember.getMemberId(), post);
    return "redirect:/community/1";
  }

}
