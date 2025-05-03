package BackendProject.repository;

import BackendProject.domain.Exercise;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository {
    Exercise save(Exercise exercise);
    Exercise findByDate(Long memberId, LocalDate exerciseDate);
    Exercise edit(Long memberId, LocalDate date, Exercise exercise);
    List<Exercise> findAll(Long memberId);
    void delete(Long memberId, LocalDate exerciseDate);
}
