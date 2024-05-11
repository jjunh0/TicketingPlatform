package com.culturelife.TicketingPlatform;

import com.culturelife.TicketingPlatform.Entity.*;
import com.culturelife.TicketingPlatform.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class TicketingPlatformApplicationTests {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PerformanceService performanceService;
	@Autowired
	private SeatService seatService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;


	@Test
	void contextLoads() {
	}

	@Test
	void testJpa() {
		testMember();
		testPost();
		testComment();

		testPerformance();
		testSeat();

//		System.out.println("allPerformanceList 시작");
//		List<Performance> allPerformanceList = performanceService.findAllPerformance();
//
//		for (Performance performance : allPerformanceList) {
//			System.out.println(performance.getPerformanceName());
//		}
	}

	private void testComment() {
		System.out.println("Comment 테스트 시작");

		for(int i=1;i<=5;i++) {
			commentService.createComment("knu",1L, "댓글 " + i);
		}
	}

	private void testSeat() {

		System.out.println("Seat 테스트 시작");

		for(int i=1;i<=5;i++) {
			Seat seat = new Seat();
			seat.setSeatName("A-" + i);
			seat.setIsBooked(false);

			seatService.createSeat(1L, seat);
		}
	}

	private void testPost() {

		System.out.println("Post 테스트 시작");

		for(int i=1;i<=8;i++) {
			Post post = new Post();
			post.setPostSubject("제목 테스트 " + i);
			post.setPostContents("내용 테스트 " + i);
			postService.createPost("knu",post);
		}
	}


	private void testPerformance() {
		
		System.out.println("Performance 테스트 시작");
		
		for(int i=1;i<=8;i++) {
			Performance performance = new Performance();
			performance.setPerformancePrice(10000 * i);
			performance.setPerformanceCount(50);
			performance.setPerformanceContents("공연설명 " + i);
			performance.setPerformanceName("공연이름 " + i);

			String dateString = String.format("2024-05-%02d", i);
			LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDateTime dateTime = date.atStartOfDay();
			performance.setPerformanceDate(dateTime);

			performanceService.createPerformance(performance);
		}

		System.out.println("Performance 페이지 테스트");
		Page<Performance> performances_0 = performanceService.searchPostPage(1);
		Page<Performance> performances_1 = performanceService.searchPostPage(2);
		Page<Performance> performances_2 = performanceService.searchPostPage(3);

		System.out.println("페이지 테스트 끝.");
	}

	private void testMember() {

		System.out.println("Memeber 테스트 시작");

		Member member = new Member();
		member.setMemberId("knu");
		member.setMemberName("강원대생");
		member.setPassword("123456");
		member.setMemberEmail("knu@naver.com");
		LocalDateTime dateTime = LocalDateTime.of(2024, 4, 14, 0, 0);
		member.setMemberCreateDate(dateTime);
		member.setUniversityAttendance(true);

		memberService.createMember(member);
	}


}
