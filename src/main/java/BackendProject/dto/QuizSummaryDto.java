package BackendProject.dto;

import lombok.Data;

@Data
public class QuizSummaryDto {
    private Long memberId;
    private Integer totalCorrectCount;
}