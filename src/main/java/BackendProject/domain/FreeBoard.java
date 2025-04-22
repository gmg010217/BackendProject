package BackendProject.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FreeBoard {
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private LocalDate creatDate;
    private List<FreeComment> comments = new ArrayList<>();
}