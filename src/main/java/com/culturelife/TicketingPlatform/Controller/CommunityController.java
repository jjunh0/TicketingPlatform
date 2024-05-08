package com.culturelife.TicketingPlatform.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {
  // 질의 응답 게시판 글 목록으로 이동
  @GetMapping("community/lists")
  public String communityListsContoller(){
    return "community/lists";
  }
  // 질의 응답 글 작성 페이지로 이동
  @GetMapping("community/write")
  public String communityWriteContoller(){
    return "community/write";
  }
  // 질의 응답 게시판의 작성된 글 페이지 보기
  // 이는 나중에 수정해야함
  @GetMapping("community/view")
  public String communityViewContoller(){
    return "community/view";
  }
}
