package BackendProject.service;

import BackendProject.domain.ChatMessage;
import BackendProject.domain.Exercise;
import BackendProject.domain.Member;
import BackendProject.dto.AiChatRequest;
import BackendProject.dto.QuizListDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GeminiServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    GeminiService geminiService;

    private Member testMember;
    @Autowired
    private ExerciseService exerciseService;

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
    @DisplayName("AI 답변")
    void addGemini() {
        // given
        AiChatRequest request = new AiChatRequest();
        request.setQuestion("안녕하세요");

        // when
        String result = geminiService.addGemini(testMember.getId(), request);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("AI 채팅 내역 불러오기")
    void makeResult() {
        // given
        AiChatRequest request = new AiChatRequest();
        request.setQuestion("안녕하세요");

        geminiService.addGemini(testMember.getId(), request);

        // when
        List<ChatMessage> result = geminiService.makeResult(testMember.getId());

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("AI 운동 루틴 추천")
    void getExerciseRecommand() {
        // when
        String result = geminiService.getExerciseRecommand(testMember.getId());

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("AI 운동 기록 자동 작성")
    void getFirstExercise() {
        // given
        Exercise exercise = new Exercise();
        exercise.setTitle("테스트 운동 제목");
        exercise.setContent("테스트 운동 내용");
        exercise.setExerciseDate(LocalDate.of(2025, 1, 2));
        exerciseService.addExercise(testMember.getId(), exercise);

        // when
        Exercise result = geminiService.getFirstExercise(testMember.getId(), LocalDate.of(2025, 1, 1));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isNotNull();
        assertThat(result.getContent()).isNotNull();
    }

    @Test
    @DisplayName("AI 퀴즈 생성")
    void getQuiz() {
        // when
        List<QuizListDto> quiz = geminiService.getQuiz(testMember.getId());

        // then
        assertThat(quiz).isNotNull();
        assertThat(quiz.size()).isEqualTo(10);
    }
}