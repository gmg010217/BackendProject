package BackendProject.dto.freeboard;

import lombok.Data;

@Data
public class AddFreeboardDto {
    private Long id;
    private String title;
    private String content;
}