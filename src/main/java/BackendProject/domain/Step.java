package BackendProject.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Step {
    private Long userId;
    private LocalDate stepDate;
    private Long bigint;
}