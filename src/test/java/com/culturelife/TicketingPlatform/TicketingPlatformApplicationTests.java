package com.culturelife.TicketingPlatform;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class TicketingPlatformApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MemberService memberService;

	@Test
	void testJpa() {
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
