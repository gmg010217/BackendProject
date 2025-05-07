package BackendProject.dto;

import BackendProject.domain.Quiz;
import lombok.Data;

import java.util.List;

@Data
public class QuizListDto {
    private String question;
    private List<String> options;
    private String answer;
}