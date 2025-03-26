package BackendProject.dto.memberdto;

import lombok.Data;

@Data
public class JoinMemberDto {
    private String emailId;
    private String password;
    private String nickname;
    private int age;
    private String gender;
    private String aboutMe;
}