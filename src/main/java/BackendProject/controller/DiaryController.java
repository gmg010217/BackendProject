package BackendProject.controller;

import BackendProject.dto.DiaryDto;
import BackendProject.service.DiaryService;
import BackendProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmind/diary/")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("{id}")
    public ResponseEntity<?> diaryList(@PathVariable("id") Long memberId) {
        List<DiaryDto> diaryDtos = diaryService.diaryList(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diaryDtos);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> diaryAdd(@PathVariable("id") Long memberId, @RequestBody DiaryDto diaryDto) {
        diaryService.diaryAdd(memberId, diaryDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @GetMapping("{id}/{diaryid}")
    public ResponseEntity<?> diaryDetail(@PathVariable("id") Long memberId, @PathVariable("diaryid") Long diaryId) {
        DiaryDto diaryDto = diaryService.diaryDetail(memberId, diaryId);

        if (diaryDto == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("존재하지 않는 다이어리 입니다");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(diaryDto);
        }
    }

    @PutMapping("{id}/{diaryid}")
    public ResponseEntity<?> diaryEdit(@PathVariable("id") Long memberId, @PathVariable("diaryid") Long diaryId, @RequestBody DiaryDto diaryDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(diaryService.diaryEdit(memberId, diaryId, diaryDto));
    }

    @DeleteMapping("{id}/{diaryid}")
    public ResponseEntity<?> diaryDelete(@PathVariable("id") Long memberId, @PathVariable("diaryid") Long diaryId) {
        diaryService.diaryDelete(memberId, diaryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}