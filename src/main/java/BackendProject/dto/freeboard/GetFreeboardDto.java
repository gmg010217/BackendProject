package BackendProject.dto.freeboard;

import BackendProject.domain.FreeComment;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetFreeboardDto {
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private List<GetFreeCommentDto> comments = new ArrayList<>();
}