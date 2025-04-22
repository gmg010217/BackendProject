package BackendProject.dto.freeboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetFreeboardsDto {
    private Long id;
    private String title;
    private LocalDate creatDate;
}