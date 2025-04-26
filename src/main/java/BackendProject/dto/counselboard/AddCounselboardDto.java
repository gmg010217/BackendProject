package BackendProject.dto.counselboard;

import lombok.Data;

@Data
public class AddCounselboardDto {
    private Long id;
    private String title;
    private String content;
}