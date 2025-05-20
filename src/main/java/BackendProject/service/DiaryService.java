package BackendProject.service;

import BackendProject.domain.Diary;
import BackendProject.dto.DiaryDto;
import BackendProject.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public List<DiaryDto> diaryList(Long memberId) {
        List<Diary> diarys = diaryRepository.findAll(memberId);
        List<DiaryDto> diaryDtos = new ArrayList<>();

        for (Diary diary : diarys) {
            DiaryDto diaryDto = new DiaryDto();
            diaryDto.setDiaryDate(diary.getDiaryDate());
            diaryDto.setTitle(diary.getTitle());
            diaryDto.setContent(diary.getContent());
            diaryDto.setId(diary.getId());
            diaryDtos.add(diaryDto);
        }

        return diaryDtos;
    }

    public Diary diaryAdd(Long memberId, DiaryDto diaryDto) {
        Diary diary = new Diary();
        diary.setMemberId(memberId);
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());
        return diaryRepository.save(diary);
    }

    public DiaryDto diaryDetail(Long memberId, Long diaryId) {
        Diary diary = diaryRepository.findById(memberId, diaryId);

        if (diary == null) {
            return null;
        }

        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setDiaryDate(diary.getDiaryDate());
        diaryDto.setTitle(diary.getTitle());
        diaryDto.setContent(diary.getContent());

        return diaryDto;
    }

    public Diary diaryEdit(Long memberId, Long diaryId, DiaryDto diaryDto) {
        Diary diary = diaryRepository.findById(memberId, diaryId);
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());

        return diaryRepository.edit(diaryId, diary);
    }

    public void diaryDelete(Long memberId, Long diaryId) {
        diaryRepository.delete(diaryId);
    }
}
