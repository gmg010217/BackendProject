package BackendProject.controller;

import BackendProject.domain.Quiz;
import BackendProject.dto.QuizListDto;
import BackendProject.service.GeminiService;
import BackendProject.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmind/quiz/")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final GeminiService geminiService;
    private final QuizService quizService;

    @GetMapping("{id}")
    public ResponseEntity<?> getQuiz(@PathVariable("id") Long memberId) {
        List<QuizListDto> quizzes = geminiService.getQuiz(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(quizzes);
    }

    @PostMapping("save/{id}")
    public ResponseEntity<?> saveQuiz(@PathVariable("id") Long memberId, @RequestBody Quiz quiz) {
        quizService.saveQuiz(memberId, quiz);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}
