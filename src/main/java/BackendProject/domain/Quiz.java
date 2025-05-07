package BackendProject.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Quiz {
    private Long id;
    private Long memberId;
    private Integer correctCount;
    private LocalDate quizDate;
}