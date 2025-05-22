package BackendProject.service;

import BackendProject.domain.Exercise;
import BackendProject.domain.Quote;
import BackendProject.repository.ExerciseRepository;
import BackendProject.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final QuoteRepository quoteRepository;

    public Quote getQuote(Long dayId) {
        return quoteRepository.findBy(dayId);
    }

    public Exercise addExercise(Long memberId, Exercise exercise) {
        exercise.setMemberId(memberId);
        return exerciseRepository.save(exercise);
    }

    public Exercise getExercise(Long memberId, LocalDate date) {
        return exerciseRepository.findByDate(memberId, date);
    }

    public Exercise editExercises(Long memberId, LocalDate date, Exercise exercise) {
        return exerciseRepository.edit(memberId, date, exercise);
    }

    public Long isFirstExercise(Long memberId) {
        List<Exercise> exercises = exerciseRepository.findAll(memberId);
        return Long.valueOf(exercises.size());
    }

    public void deleteExercise(Long memberId, LocalDate date) {
        exerciseRepository.delete(memberId, date);
    }
}