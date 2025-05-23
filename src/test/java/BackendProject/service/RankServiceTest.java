package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.domain.Rank;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RankServiceTest {

    @Autowired
    RankService rankService;

    @Autowired
    MemberService memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setEmailId("testEmail@gmail.com");
        testMember.setPassword("1234");
        testMember.setNickName("테스트 유저");
        testMember.setGender("남자");
        testMember.setAge(25);
        testMember.setAboutMe("안녕하세요");
        memberService.signUp(testMember);
    }

    @AfterEach
    void reset() {
        if (testMember != null) {
            memberService.delete(testMember.getId());
            testMember = null;
        }
    }

    @Test
    @DisplayName("랭킹 조회")
    void getRank() {
        // when
        List<Rank> rank = rankService.getRank(testMember.getId());

        // then
        assertThat(rank).isNotNull();
    }
}