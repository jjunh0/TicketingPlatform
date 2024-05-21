package com.culturelife.TicketingPlatform.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {
  // 질의 응답 게시판 글 목록으로 이동
  @GetMapping("community")
  public String communityListsController(){
    return "community";
  }
  // 질의 응답 글 작성 페이지로 이동
  @GetMapping("createPost")
  public String communityWriteController(){
    return "createpost";
  }
  // 질의 응답 게시판의 작성된 글 페이지 보기
  // 이는 나중에 수정해야함
  @GetMapping("viewPoster")
  public String communityViewController(){ return "post"; }
}
