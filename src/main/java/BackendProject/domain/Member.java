package BackendProject.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Member {
    private Long id;
    @NotBlank
    private String emailId;
    @NotBlank
    private String password;

    @NotBlank
    private String nickName;
    @NotNull
    private Integer age;
    @NotBlank
    private String gender;
    @NotBlank
    private String aboutMe;
}