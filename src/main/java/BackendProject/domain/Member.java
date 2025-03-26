package BackendProject.domain;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String emailId;
    private String password;
    private String nickname;
    private int age;
    private String gender;
    private String aboutMe;
}