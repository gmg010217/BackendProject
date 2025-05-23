package BackendProject.service;

import BackendProject.domain.FreeBoard;
import BackendProject.domain.FreeComment;
import BackendProject.domain.Member;
import BackendProject.dto.freeboard.AddFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardsDto;
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
class FreeBoardServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    FreeBoardService freeBoardService;

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
    @DisplayName("자유게시판 게시글 작성")
    void addFreeBoard() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");

        // when
        FreeBoard result = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(addFreeboardDto.getTitle());
        assertThat(result.getContent()).isEqualTo(addFreeboardDto.getContent());

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), result.getId());
    }

    @Test
    @DisplayName("자유게시판 게시글 리스트 조회")
    void getFreeBoards() {
        // given
        AddFreeboardDto addFreeboardDto1 = new AddFreeboardDto();
        addFreeboardDto1.setTitle("테스트 게시글 제목 1");
        addFreeboardDto1.setContent("테스트 게시글 내용 1");
        FreeBoard freeBoard1 = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto1);

        AddFreeboardDto addFreeboardDto2 = new AddFreeboardDto();
        addFreeboardDto2.setTitle("테스트 게시글 제목 2");
        addFreeboardDto2.setContent("테스트 게시글 내용 2");
        FreeBoard freeBoard2 = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto2);

        // when
        List<GetFreeboardsDto> freeBoards = freeBoardService.getFreeBoards();

        // then
        assertThat(freeBoards).isNotNull();
        assertThat(freeBoards.size()).isEqualTo(2);
        assertThat(freeBoards.get(0).getTitle()).isEqualTo(freeBoard1.getTitle());
        assertThat(freeBoards.get(1).getTitle()).isEqualTo(freeBoard2.getTitle());

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard1.getId());
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard2.getId());
    }

    @Test
    @DisplayName("자유게시판 게시글 제목으로 검색")
    void searchByTitle() {
        // given
        AddFreeboardDto addFreeboardDto1 = new AddFreeboardDto();
        addFreeboardDto1.setTitle("테스트 게시글 제목 1");
        addFreeboardDto1.setContent("테스트 게시글 내용 1");
        FreeBoard freeBoard1 = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto1);

        AddFreeboardDto addFreeboardDto2 = new AddFreeboardDto();
        addFreeboardDto2.setTitle("테스트 게시글 제목 2");
        addFreeboardDto2.setContent("테스트 게시글 내용 2");
        FreeBoard freeBoard2 = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto2);

        // when
        List<GetFreeboardsDto> searchResults = freeBoardService.searchByTitle("테스트");

        // then
        assertThat(searchResults).isNotNull();
        assertThat(searchResults.size()).isEqualTo(2);
        assertThat(searchResults.get(0).getTitle()).isEqualTo(freeBoard1.getTitle());
        assertThat(searchResults.get(1).getTitle()).isEqualTo(freeBoard2.getTitle());

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard1.getId());
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard2.getId());
    }

    @Test
    @DisplayName("자유 게시판 게시글 상세 조회_성공")
    void getFreeBoardSuccess() {
        // given
        AddFreeboardDto addFreeboardDto1 = new AddFreeboardDto();
        addFreeboardDto1.setTitle("테스트 게시글 제목 1");
        addFreeboardDto1.setContent("테스트 게시글 내용 1");
        FreeBoard freeBoard1 = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto1);

        // when
        GetFreeboardDto freeBoard = freeBoardService.getFreeBoard(testMember.getId(), freeBoard1.getId());

        // then
        assertThat(freeBoard).isNotNull();
        assertThat(freeBoard.getTitle()).isEqualTo(freeBoard1.getTitle());
        assertThat(freeBoard.getContent()).isEqualTo(freeBoard1.getContent());

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard1.getId());
    }

    @Test
    @DisplayName("자유 게시판 게시글 상세 조회_실패")
    void getFreeBoardFail() {
        // when
        GetFreeboardDto result = freeBoardService.getFreeBoard(testMember.getId(), 999L);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("자유 게시판 댓글 추가")
    void addComment() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        FreeComment result = freeBoardService.addComment(testMember.getId(), freeBoard.getId(), "테스트 댓글 내용");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("테스트 댓글 내용");

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 삭제_성공")
    void deleteFreeBoardSuccess() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        String result = freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("success");
    }

    @Test
    @DisplayName("자유 게시판 삭제_실패")
    void deleteFreeBoardFail() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        String result = freeBoardService.deleteFreeBoard(999L, freeBoard.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("fail");

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 수정_성공")
    void editFreeBoardSuccess() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        AddFreeboardDto editDto = new AddFreeboardDto();
        editDto.setTitle("수정된 제목");
        editDto.setContent("수정된 내용");
        String result = freeBoardService.editFreeBoard(testMember.getId(), freeBoard.getId(), editDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("success");

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 수정_실패")
    void editFreeBoardFail() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        AddFreeboardDto editDto = new AddFreeboardDto();
        editDto.setTitle("수정된 제목");
        editDto.setContent("수정된 내용");
        String result = freeBoardService.editFreeBoard(999L, freeBoard.getId(), editDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("fail");

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 수정을 위한 데이터 조회_성공")
    void getEditFreeBoardSuccess() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        AddFreeboardDto result = freeBoardService.getEditFreeBoard(testMember.getId(), freeBoard.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(freeBoard.getTitle());
        assertThat(result.getContent()).isEqualTo(freeBoard.getContent());

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 수정을 위한 데이터 조회_실패")
    void getEditFreeBoardFail() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        // when
        AddFreeboardDto result = freeBoardService.getEditFreeBoard(999L, freeBoard.getId());

        // then
        assertThat(result).isNull();

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 댓글 삭제_성공")
    void deleteCommentSuccess() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        FreeComment comment = freeBoardService.addComment(testMember.getId(), freeBoard.getId(), "테스트 댓글 내용");

        // when
        String result = freeBoardService.deleteComment(testMember.getId(), comment.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("success");

        // clean up
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }

    @Test
    @DisplayName("자유 게시판 댓글 삭제_실패")
    void deleteCommentFail() {
        // given
        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle("테스트 게시글 제목");
        addFreeboardDto.setContent("테스트 게시글 내용");
        FreeBoard freeBoard = freeBoardService.addFreeBoard(testMember.getId(), addFreeboardDto);

        FreeComment comment = freeBoardService.addComment(testMember.getId(), freeBoard.getId(), "테스트 댓글 내용");

        // when
        String result = freeBoardService.deleteComment(999L, comment.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("fail");

        // clean up
        freeBoardService.deleteComment(testMember.getId(), comment.getId());
        freeBoardService.deleteFreeBoard(testMember.getId(), freeBoard.getId());
    }
}