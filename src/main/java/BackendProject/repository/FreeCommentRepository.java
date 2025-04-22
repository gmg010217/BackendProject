package BackendProject.repository;

import BackendProject.domain.Exercise;
import BackendProject.domain.FreeComment;

import java.time.LocalDate;
import java.util.List;

public interface FreeCommentRepository {
    FreeComment save(FreeComment exercise);
    FreeComment findById(Long commentId);
    void delete(Long commentId);
    List<FreeComment> getBoardComment(Long boardId);
}