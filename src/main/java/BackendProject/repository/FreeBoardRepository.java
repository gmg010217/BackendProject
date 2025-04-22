package BackendProject.repository;

import BackendProject.domain.FreeBoard;

import java.time.LocalDate;
import java.util.List;

public interface FreeBoardRepository {
    FreeBoard save(FreeBoard freeBoard);
    FreeBoard findById(Long boardId);
    FreeBoard edit(FreeBoard freeBoard);
    void delete(Long boardId);
    List<FreeBoard> findByTitle(String title);
    List<FreeBoard> findAll();
    LocalDate findCreateDateById(Long id);
}
