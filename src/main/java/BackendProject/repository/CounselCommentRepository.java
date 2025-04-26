package BackendProject.repository;

import BackendProject.domain.CounselComment;
import BackendProject.domain.FreeComment;

import java.util.List;

public interface CounselCommentRepository {
    CounselComment save(CounselComment counselComment);
    CounselComment findById(Long commentId);
    void delete(Long commentId);
    List<CounselComment> getBoardComment(Long boardId);
}