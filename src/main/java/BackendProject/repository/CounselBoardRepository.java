package BackendProject.repository;

import BackendProject.domain.CounselBoard;
import BackendProject.domain.FreeBoard;

import java.time.LocalDate;
import java.util.List;

public interface CounselBoardRepository {
    CounselBoard save(CounselBoard counselBoard);
    CounselBoard findById(Long boardId);
    CounselBoard edit(CounselBoard counselBoard);
    void delete(Long boardId);
    List<CounselBoard> findByTitle(String title);
    List<CounselBoard> findAll();
    LocalDate findCreateDateById(Long id);
}
