package BackendProject.controller;

import BackendProject.domain.Exercise;
import BackendProject.domain.Quote;
import BackendProject.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/healthmind/exercise/")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("{dayid}")
    public ResponseEntity<?> getQuote(@PathVariable("dayid") Long dayId) {
        Quote quote = exerciseService.getQuote(dayId);

        if (quote == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("올바른 날짜를 입력해주세요");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(quote);
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addExercise(@PathVariable("id") Long memberId, @RequestBody Exercise exercise) {
        exerciseService.addExercise(memberId, exercise);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @GetMapping("{id}/{date}")
    public ResponseEntity<?> getExercise(@PathVariable("id") Long memberId, @PathVariable("date") LocalDate date) {
        Exercise exercise = exerciseService.getExercise(memberId, date);

        if (exercise == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("OK");
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(exercise);
        }
    }

    @PostMapping("{id}/{date}")
    public ResponseEntity<?> editExercises(@PathVariable("id") Long memberId, @PathVariable("date") LocalDate date, @RequestBody Exercise exercise) {
        exerciseService.editExercises(memberId, date, exercise);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}