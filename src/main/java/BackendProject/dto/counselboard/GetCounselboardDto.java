package BackendProject.dto.counselboard;

import BackendProject.dto.freeboard.GetFreeCommentDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCounselboardDto {
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private List<GetCounselCommentDto> comments = new ArrayList<>();
}