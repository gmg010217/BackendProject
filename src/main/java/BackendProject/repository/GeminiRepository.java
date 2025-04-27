package BackendProject.repository;

import BackendProject.domain.ChatMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GeminiRepository {
    ChatMessage save(ChatMessage chatMessage);
    List<ChatMessage> findAll(Long memberId);
    void delete(Long id);
    LocalDateTime findCreateDateById(Long id);
}