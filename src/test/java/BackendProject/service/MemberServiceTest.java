package BackendProject.service;

import BackendProject.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

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
    void signUp() {
        // given
        Member member = new Member();
        member.setEmailId("t@gmail.com");
        member.setPassword("1234");
        member.setNickName("테스트 유저");
        member.setGender("남자");
        member.setAge(25);
        member.setAboutMe("안녕하세요");

        // when
        Member findMember = memberService.signUp(member);

        // then
        assertThat(findMember).isNotNull();
        assertThat(findMember.getEmailId()).isEqualTo(member.getEmailId());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());

        memberService.delete(findMember.getId());
    }

    @Test
    void login() {
        // given


        // when


        // then


    }

    @Test
    void info() {
    }

    @Test
    void editInfo() {
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}