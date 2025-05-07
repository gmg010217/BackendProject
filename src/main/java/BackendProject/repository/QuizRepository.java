package BackendProject.repository;

import BackendProject.domain.Quiz;

import java.util.List;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    List<Quiz> findAll(Long memberId);
}