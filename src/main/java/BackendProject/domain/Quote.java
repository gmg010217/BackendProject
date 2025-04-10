package BackendProject.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Quote {
    @NotNull
    private Long qday;
    private String content;
}