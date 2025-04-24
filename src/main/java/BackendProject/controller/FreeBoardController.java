package BackendProject.controller;

import BackendProject.dto.freeboard.AddFreeCommentDto;
import BackendProject.dto.freeboard.AddFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardsDto;
import BackendProject.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmind/freeboard/")
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @GetMapping()
    public ResponseEntity<?> getFreeBoards() {
        List<GetFreeboardsDto> freeBoards = freeBoardService.getFreeBoards();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(freeBoards);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addFreeBoard(@PathVariable("id") Long memberId, @RequestBody AddFreeboardDto addFreeboardDto) {
        freeBoardService.addFreeBoard(memberId, addFreeboardDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @GetMapping("search")
    public ResponseEntity<?> searchFreeBoards(@RequestParam("title") String title) {
        List<GetFreeboardsDto> results = freeBoardService.searchByTitle(title);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(results);
    }

    @GetMapping("{id}/{boardid}")
    public ResponseEntity<?> getFreeboard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        GetFreeboardDto getFreeboardDto = freeBoardService.getFreeBoard(memberId, boardId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getFreeboardDto);
    }

    @GetMapping("edit/{id}/{boardid}")
    public ResponseEntity<?> getEditFreeBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        AddFreeboardDto addFreeboardDto = freeBoardService.getEditFreeBoard(memberId, boardId);

        if (addFreeboardDto == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("수정할 수 없습니다");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addFreeboardDto);
    }

    @PostMapping("edit/{id}/{boardid}")
    public ResponseEntity<?> editFreeBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId, @RequestBody AddFreeboardDto addFreeboardDto) {
        String result = freeBoardService.editFreeBoard(memberId, boardId, addFreeboardDto);

        if (result.equals("fail")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("수정할 수 없습니다");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("OK");
        }
    }

    @DeleteMapping("{id}/{boardid}")
    public ResponseEntity<?> deleteFreeBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        String result = freeBoardService.deleteFreeBoard(memberId, boardId);

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
    public ResponseEntity<?> addComment(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardid, @RequestBody AddFreeCommentDto comment) {
        freeBoardService.addComment(memberId, boardid, comment.getComment());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @DeleteMapping("comment/{id}/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long memberId, @PathVariable("commentid") Long commentId) {
        String result = freeBoardService.deleteComment(memberId, commentId);

        if (result.equals("fail")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("삭제할 수 없습니다");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("OK");
        }
    }
}