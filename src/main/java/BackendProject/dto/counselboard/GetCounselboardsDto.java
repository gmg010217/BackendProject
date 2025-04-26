package BackendProject.dto.counselboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetCounselboardsDto {
    private Long id;
    private String title;
    private LocalDate creatDate;
}