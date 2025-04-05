package BackendProject.repository;

import BackendProject.domain.Diary;
import BackendProject.domain.Member;
import BackendProject.dto.MemberEditInfoDto;

import java.util.List;

public interface DiaryRepository {
    Diary save(Diary diary);
    Diary findById(Long diaryId);
    Diary findById(Long memberId, Long diaryId);
    Diary edit(Long memberId, Diary diaryId);
    List<Diary> findAll(Long memberId);
    void delete(Long diaryId);
}
