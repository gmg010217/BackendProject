package BackendProject.repository;

import BackendProject.domain.Quiz;
import BackendProject.dto.QuizSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    List<QuizSummaryDto> findAll();
    boolean existsByMemberIdAndDate(Long memberId, LocalDate date);
}