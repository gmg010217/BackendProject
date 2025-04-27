package BackendProject.controller;

import BackendProject.domain.ChatMessage;
import BackendProject.dto.AiChatRequest;
import BackendProject.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmind/aichat/")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("{id}")
    public ResponseEntity<List<ChatMessage>> getGemini(@PathVariable Long id) {
        List<ChatMessage> result = geminiService.makeResult(id);

        if (result != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<List<ChatMessage>> addGemini(@PathVariable("id") Long memberId, @RequestBody AiChatRequest request) {
        List<ChatMessage> result = geminiService.addGemini(memberId, request);

        if (result != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
