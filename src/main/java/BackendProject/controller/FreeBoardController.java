package BackendProject.controller;

import BackendProject.dto.freeboard.AddFreeboardDto;
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
        return null;
    }

    @PostMapping("{id}/{boardid}")
    public ResponseEntity<?> editFreeBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        return null;
    }

    @DeleteMapping("{id}/{boardid}")
    public ResponseEntity<?> deleteFreeBoard(@PathVariable("id") Long memberId, @PathVariable("boardid") Long boardId) {
        return null;
    }

    @GetMapping("comment/{id}/{boardid}")
    public ResponseEntity<?> addComment(@PathVariable("id") Long memberId, @PathVariable("board") Long boardid) {
        return null;
    }

    @DeleteMapping("comment/{id}/{boardid}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long memberId, @PathVariable("board") Long boardid) {
        return null;
    }
}