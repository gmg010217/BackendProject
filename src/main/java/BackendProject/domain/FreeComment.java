package BackendProject.domain;

import lombok.Data;

@Data
public class FreeComment {
    private Long id;
    private Long writerId;
    private Long boardId;
    private String content;
}