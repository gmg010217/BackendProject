package BackendProject.service;

import BackendProject.domain.Quiz;
import BackendProject.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz saveQuiz(Long memberId, Quiz quiz) {
        quiz.setMemberId(memberId);
        return quizRepository.save(quiz);
    }
}