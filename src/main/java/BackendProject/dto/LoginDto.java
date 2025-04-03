package BackendProject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    private Long id;
    @NotBlank
    private String emailId;
    @NotBlank
    private String password;
}
