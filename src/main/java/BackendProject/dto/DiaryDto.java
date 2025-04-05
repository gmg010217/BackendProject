package BackendProject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaryDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDate diaryDate;
}