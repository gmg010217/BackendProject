package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.dto.MemberEditInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("회원가입_성공")
    void signUpSuccess() {
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
    @DisplayName("로그인_성공")
    void loginSuccess() {
        // when
        Member loginMember = memberService.login(testMember.getEmailId(), testMember.getPassword());

        // then
        assertThat(loginMember).isNotNull();
        assertThat(loginMember.getEmailId()).isEqualTo(testMember.getEmailId());
        assertThat(loginMember.getPassword()).isEqualTo(testMember.getPassword());
    }

    @Test
    @DisplayName("로그인_실패")
    void loginFail() {
        // when
        Member loginMember = memberService.login(testMember.getEmailId(), "wrongPassword");

        // then
        assertThat(loginMember).isNull();
    }

    @Test
    @DisplayName("회원정보조회_성공")
    void infoSuccess() {
        // when
        Member member = memberService.info(testMember.getId());

        // then
        assertThat(member).isNotNull();
        assertThat(member.getEmailId()).isEqualTo(testMember.getEmailId());
        assertThat(member.getNickName()).isEqualTo(testMember.getNickName());
    }

    @Test
    @DisplayName("회원정보조회_실패")
    void infoFail() {
        // when
        Member member = memberService.info(999L); // 존재하지 않는 ID

        // then
        assertThat(member).isNull();
    }

    @Test
    @DisplayName("회원정보수정_성공")
    void editSuccess() {
        // given
        MemberEditInfoDto memberEditInfoDto = new MemberEditInfoDto();
        memberEditInfoDto.setNickName("수정된 유저");
        memberEditInfoDto.setAge(30);
        memberEditInfoDto.setGender("여자");
        memberEditInfoDto.setAboutMe("안녕하세요 수정되었습니다");

        // when
        Member updatedMember = memberService.edit(testMember.getId(), memberEditInfoDto);

        // then
        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.getNickName()).isEqualTo(memberEditInfoDto.getNickName());
        assertThat(updatedMember.getAge()).isEqualTo(memberEditInfoDto.getAge());
    }

    @Test
    @DisplayName("회원정보수정_실패")
    void editFail() {
        // given
        MemberEditInfoDto memberEditInfoDto = new MemberEditInfoDto();
        memberEditInfoDto.setNickName("수정된 유저");
        memberEditInfoDto.setAge(30);
        memberEditInfoDto.setGender("여자");
        memberEditInfoDto.setAboutMe("안녕하세요 수정되었습니다");

        // when
        Member updatedMember = memberService.edit(999L, memberEditInfoDto); // 존재하지 않는 ID

        // then
        assertThat(updatedMember).isNull();
    }
}