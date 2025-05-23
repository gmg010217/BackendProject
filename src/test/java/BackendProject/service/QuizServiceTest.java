package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.domain.Quiz;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class QuizServiceTest {

    @Autowired
    QuizService quizService;

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
    @DisplayName("퀴즈 저장")
    void saveQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setQuizDate(LocalDate.of(2025, 05, 12));
        quiz.setMemberId(testMember.getId());
        quiz.setCorrectCount(10);

        // when
        Quiz result = quizService.saveQuiz(testMember.getId(), quiz);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQuizDate()).isEqualTo(quiz.getQuizDate());
        assertThat(result.getMemberId()).isEqualTo(quiz.getMemberId());
        assertThat(result.getCorrectCount()).isEqualTo(quiz.getCorrectCount());
    }
}