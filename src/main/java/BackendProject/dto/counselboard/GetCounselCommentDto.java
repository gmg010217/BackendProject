package BackendProject.dto.counselboard;

import lombok.Data;

@Data
public class GetCounselCommentDto {
    private Long id;
    private String writerName;
    private Long writerId;
    private String content;
}
