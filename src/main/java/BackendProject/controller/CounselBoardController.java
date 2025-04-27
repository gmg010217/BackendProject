package BackendProject.controller;

import BackendProject.dto.counselboard.AddCounselCommentDto;
import BackendProject.dto.counselboard.AddCounselboardDto;
import BackendProject.dto.counselboard.GetCounselboardDto;
import BackendProject.dto.counselboard.GetCounselboardsDto;
import BackendProject.service.CounselBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmind/counselboard/")
@RequiredArgsConstructor
public class CounselBoardController {

    private final CounselBoardService counselBoardService;

    @GetMapping()
    public ResponseEntity<?> getCounselBoards() {
        List<GetCounselboardsDto> counselBoards = counselBoardService.getCounselBoards();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(counselBoards);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addCounselBoard(@PathVariable("id") Long memberId, @RequestBody AddCounselboardDto addCounselboardDto) {
        counselBoardService.addCounselBoard(memberId, addCounselboardDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @GetMapping("search")
    public ResponseEntity<?> searchCounselBoards(@RequestParam("title") String title) {
        List<GetCounselboardsDto> results = counselBoardService.searchByTitle(title);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @GetMapping("{id}/{boardid}")
    public ResponseEntity<?> getCounselboard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        GetCounselboardDto getCounselboardDto = counselBoardService.getCounselBoard(memberId, boardId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getCounselboardDto);
    }

    @GetMapping("edit/{id}/{boardid}")
    public ResponseEntity<?> getEditCounselBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        AddCounselboardDto addCounselboardDto = counselBoardService.getEditCounselBoard(memberId, boardId);

        if (addCounselboardDto == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("수정할 수 없습니다");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addCounselboardDto);
    }

    @PostMapping("edit/{id}/{boardid}")
    public ResponseEntity<?> editCounselBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId, @RequestBody AddCounselboardDto addCounselboardDto) {
        String result = counselBoardService.editCounselBoard(memberId, boardId, addCounselboardDto);

        if (result.equals("fail")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("수정할 수 없습니다");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @DeleteMapping("{id}/{boardid}")
    public ResponseEntity<?> deleteCounselBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        String result = counselBoardService.deleteCounselBoard(memberId, boardId);

        if (result.equals("fail")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("삭제할 수 없습니다");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @PostMapping("comment/{id}/{boardid}")
    public ResponseEntity<?> addComment(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId, @RequestBody AddCounselCommentDto comment) {
        counselBoardService.addComment(memberId, boardId, comment.getComment());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @DeleteMapping("comment/{id}/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long memberId, @PathVariable("commentid") Long commentId) {
        String result = counselBoardService.deleteComment(memberId, commentId);

        if (result.equals("fail")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("삭제할 수 없습니다");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}
