package BackendProject.dto.freeboard;

import lombok.Data;

@Data
public class GetFreeCommentDto {
    private Long id;
    private String writerName;
    private Long writerId;
    private String content;
}
