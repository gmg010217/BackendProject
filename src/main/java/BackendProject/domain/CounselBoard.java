package BackendProject.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CounselBoard {
    private Long id;
    private Long writerId;
    private String title;
    private String content;
    private LocalDate creatDate;
    private List<CounselComment> comments = new ArrayList<>();
}