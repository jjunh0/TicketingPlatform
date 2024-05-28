package com.culturelife.TicketingPlatform.Entity.dto;

import com.culturelife.TicketingPlatform.Entity.Comment;
import com.culturelife.TicketingPlatform.Entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO {
  private Long id;
  private String postSubject;
  private String postContents;
  private Long viewCount = 0L;
  private Member member;
  private List<Comment> commentList;
  private LocalDateTime postCreateDate;
  private LocalDateTime postUpdateDate;
}
