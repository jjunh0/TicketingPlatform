package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Post;
import com.culturelife.TicketingPlatform.Entity.dto.PostDTO;
import com.culturelife.TicketingPlatform.Service.AuthenticatedUserService;
import com.culturelife.TicketingPlatform.Service.CommentService;
import com.culturelife.TicketingPlatform.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class CommunityController {
  private final PostService postService;
  private final AuthenticatedUserService userService;
  private final CommentService commentService;

  // 요청한 페이지의 글 목록을 model에 추가하여 이동
  @GetMapping("/community/{pageNumber}")
  public String communityPageController(@PathVariable("pageNumber") int pageNumber, Model model){
    Page<Post> postList = postService.readPostPage(pageNumber);
    int totalPage = postService.readAllPosts().size();
    if(totalPage % 10 > 0) totalPage += 10;
    totalPage /= 10;
    model.addAttribute("Postlist", postList);
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalPages", totalPage);
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
  @GetMapping("/post/{postId}")
  public String postViewController(@PathVariable("postId") Long postId, Model model){
    Post post = postService.readPostById(postId);
    String currentMemberId;
    if(userService.getCurrentMember().isPresent()) currentMemberId = userService.getCurrentMemberId();
    else{
      System.out.println("유효하지않은 접근");
      return "redirect:/community/1";
    }
    model.addAttribute("post", post);
    model.addAttribute("currentMemberId", currentMemberId);
    return "post";
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

  @PostMapping("submitComment/{postId}")
  public String commentCreateController(@PathVariable("postId")Long postId, @RequestParam("commentContent") String commentContent){
    String currentMemberId;
    if(userService.getCurrentMember().isPresent()) currentMemberId = userService.getCurrentMemberId();
    else{
      System.out.println("유효하지않은 접근");
      return "redirect:/post/"+postId;
    }
    commentService.createComment(currentMemberId, postId, commentContent);
    return "redirect:/post/"+postId;
  }

  @GetMapping("/search")
  public String searchPostController(@RequestParam("query")String query, Model model){
    Page<Post> postList = postService.searchPostPage(1, query);
    model.addAttribute("Postlist", postList);
    return "community";
  }



}
