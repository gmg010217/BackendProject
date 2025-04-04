package BackendProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberEditInfoDto {
    @NotBlank
    private String nickName;
    @NotNull
    private Integer age;
    @NotBlank
    private String gender;
    @NotBlank
    private String aboutMe;
}