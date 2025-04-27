package BackendProject.controller;

import BackendProject.dto.GeminiResponseDto;
import BackendProject.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthmind/aichat/")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("{id}")
    public ResponseEntity<String> addGemini(@PathVariable("id") Long memberId) {
        String result = geminiService.addGemini(memberId);

        if (result != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gemini 호출 중 서버 오류 발생");
        }
    }
}
