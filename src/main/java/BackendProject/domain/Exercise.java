package BackendProject.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Exercise {
    private Long id;
    private Long memberId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDate exerciseDate;
}