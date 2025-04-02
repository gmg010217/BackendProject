package BackendProject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank
    private String emailId;
    @NotBlank
    private String password;
}
