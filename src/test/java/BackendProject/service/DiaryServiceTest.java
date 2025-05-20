package BackendProject.service;

import BackendProject.domain.Diary;
import BackendProject.domain.Member;
import BackendProject.dto.DiaryDto;
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
class DiaryServiceTest {

    @Autowired
    DiaryService diaryService;

    @Autowired
    MemberService memberService;

    private Member testMember;
    private Diary[] testDiaryList;

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

        testDiaryList = new Diary[3];
        for (int i = 0; i < testDiaryList.length; i++) {
            DiaryDto dto = new DiaryDto();
            dto.setTitle("테스트 다이어리 " + (i + 1) + " 제목");
            dto.setContent("테스트 내용 " + (i + 1));
            Diary savedDiary = diaryService.diaryAdd(testMember.getId(), dto);
            testDiaryList[i] = savedDiary;
        }
    }

    @AfterEach
    void reset() {
        if (testDiaryList != null) {
            for (Diary diary : testDiaryList) {
                diaryService.diaryDelete(testMember.getId(), diary.getId());
            }
            testDiaryList = null;
        }
        if (testMember != null) {
            memberService.delete(testMember.getId());
            testMember = null;
        }
    }

    @Test
    @DisplayName("다이어리 리스트 조회_성공")
    void diaryList() {
        // when
        List<DiaryDto> diaryDtos = diaryService.diaryList(testMember.getId());

        // then
        assertThat(diaryDtos).isNotNull();
        assertThat(diaryDtos.size()).isEqualTo(testDiaryList.length);
    }

    @Test
    @DisplayName("다이어리 추가_성공")
    void diaryAdd() {
        // given
        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setTitle("테스트 다이어리 제목");
        diaryDto.setContent("테스트 내용");

        // when
        Diary savedDiary = diaryService.diaryAdd(testMember.getId(), diaryDto);

        // then
        assertThat(savedDiary).isNotNull();
        assertThat(savedDiary.getTitle()).isEqualTo(diaryDto.getTitle());
        assertThat(savedDiary.getContent()).isEqualTo(diaryDto.getContent());
    }

    @Test
    @DisplayName("다이어리 상세 조회_성공")
    void diaryDetailSuccess() {
        // when
        DiaryDto diaryDto = diaryService.diaryDetail(testMember.getId(), testDiaryList[0].getId());

        // then
        assertThat(diaryDto).isNotNull();
        assertThat(diaryDto.getTitle()).isEqualTo(testDiaryList[0].getTitle());
        assertThat(diaryDto.getContent()).isEqualTo(testDiaryList[0].getContent());
    }

    @Test
    @DisplayName("다이어리 상세 조회_실패")
    void diaryDetailFail() {
        // when
        DiaryDto diaryDto = diaryService.diaryDetail(testMember.getId(), 99999L);

        // then
        assertThat(diaryDto).isNull();
    }

    @Test
    @DisplayName("다이어리 수정_성공")
    void diaryEdit() {
        // given
        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setTitle("수정된 제목");
        diaryDto.setContent("수정된 내용");

        // when
        Diary updatedDiary = diaryService.diaryEdit(testMember.getId(), testDiaryList[0].getId(), diaryDto);

        // then
        assertThat(updatedDiary).isNotNull();
        assertThat(updatedDiary.getTitle()).isEqualTo(diaryDto.getTitle());
        assertThat(updatedDiary.getContent()).isEqualTo(diaryDto.getContent());
    }

    @Test
    @DisplayName("다이어리 삭제_성공")
    void diaryDelete() {
        // when
        diaryService.diaryDelete(testMember.getId(), testDiaryList[0].getId());

        // then
        DiaryDto diaryDto = diaryService.diaryDetail(testMember.getId(), testDiaryList[0].getId());
        assertThat(diaryDto).isNull();
    }
}