package BackendProject.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private Long id;
    private Long writerId;
    private String sender;
    private String content;
    private LocalDateTime createAt;
}