package BackendProject.service;

import BackendProject.domain.Diary;
import BackendProject.domain.Exercise;
import BackendProject.domain.Member;
import BackendProject.domain.Quote;
import BackendProject.dto.DiaryDto;
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
class ExerciseServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    ExerciseService exerciseService;

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
    @DisplayName("명언 조회_성공")
    void getQuoteSuccess() {
        // when
        Quote quote = exerciseService.getQuote(1L);

        // then
        assertThat(quote).isNotNull();
    }

    @Test
    @DisplayName("명언 조회_실패")
    void getQuoteFail() {
        // when
        Quote quote = exerciseService.getQuote(999L);

        // then
        assertThat(quote).isNull();
    }

    @Test
    @DisplayName("운동 추가_성공")
    void addExercise() {
        // given
        Exercise exercise = new Exercise();
        exercise.setTitle("운동 제목");
        exercise.setContent("운동 내용");
        exercise.setExerciseDate(LocalDate.of(2025, 05, 20));

        // when
        Exercise savedExercise = exerciseService.addExercise(testMember.getId(), exercise);

        // then
        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getTitle()).isEqualTo(exercise.getTitle());
        assertThat(savedExercise.getContent()).isEqualTo(exercise.getContent());

        // Clean up
        exerciseService.deleteExercise(testMember.getId(), LocalDate.of(2025, 05, 20));
    }

    @Test
    @DisplayName("운동 상세 조회_성공")
    void getExerciseSuccess() {
        // given
        Exercise exercise = new Exercise();
        exercise.setTitle("운동 제목");
        exercise.setContent("운동 내용");
        exercise.setExerciseDate(LocalDate.of(2025, 05, 20));

        exerciseService.addExercise(testMember.getId(), exercise);

        // when
        Exercise result = exerciseService.getExercise(testMember.getId(), LocalDate.of(2025, 05, 20));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(exercise.getTitle());
        assertThat(result.getContent()).isEqualTo(exercise.getContent());

        // Clean up
        exerciseService.deleteExercise(testMember.getId(), LocalDate.of(2025, 05, 20));
    }

    @Test
    @DisplayName("운동 상세 조회_실패")
    void getExerciseFail() {
        // when
        Exercise result = exerciseService.getExercise(testMember.getId(), LocalDate.of(2030, 05, 20));

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("운동 기록 수정_성공")
    void editExercisesSuccess() {
        // given
        Exercise exercise = new Exercise();
        exercise.setTitle("운동 제목");
        exercise.setContent("운동 내용");
        exercise.setExerciseDate(LocalDate.of(2025, 05, 20));

        Exercise editExercise = exerciseService.addExercise(testMember.getId(), exercise);
        editExercise.setTitle("수정된 운동 제목");
        editExercise.setContent("수정된 운동 내용");

        // when
        Exercise result = exerciseService.editExercises(testMember.getId(), LocalDate.of(2025, 05, 20), editExercise);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(editExercise.getTitle());
        assertThat(result.getContent()).isEqualTo(editExercise.getContent());

        // Clean up
        exerciseService.deleteExercise(testMember.getId(), LocalDate.of(2025, 05, 20));
    }

    @Test
    @DisplayName("운동 기록 수정_실패")
    void editExercisesFail() {
        // given
        Exercise exercise = new Exercise();
        exercise.setTitle("운동 제목");
        exercise.setContent("운동 내용");
        exercise.setExerciseDate(LocalDate.of(2025, 05, 20));

        // when
        Exercise result = exerciseService.editExercises(testMember.getId(), LocalDate.of(2030, 05, 20), exercise);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("최초 운동 기록 확인")
    void isFirstExercise() {
        // when
        Long result = exerciseService.isFirstExercise(testMember.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(0);
    }
}