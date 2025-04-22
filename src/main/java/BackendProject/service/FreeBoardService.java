package BackendProject.service;

import BackendProject.domain.FreeBoard;
import BackendProject.dto.freeboard.AddFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardsDto;
import BackendProject.repository.FreeBoardRepository;
import BackendProject.repository.FreeCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeCommentRepository freeCommentRepository;

    public void addFreeBoard(Long memberId, AddFreeboardDto addFreeboardDto) {
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setWriterId(memberId);
        freeBoard.setTitle(addFreeboardDto.getTitle());
        freeBoard.setContent(addFreeboardDto.getContent());
        freeBoardRepository.save(freeBoard);
    }

    public List<GetFreeboardsDto> getFreeBoards() {
        List<FreeBoard> freeBoards = freeBoardRepository.findAll();
        for (FreeBoard freeBoard : freeBoards) {
            LocalDate date = freeBoardRepository.findCreateDateById(freeBoard.getId());
            freeBoard.setCreatDate(date);
        }

        List<GetFreeboardsDto> freeboardsDtos = new ArrayList<>();

        for (FreeBoard freeBoard : freeBoards) {
            GetFreeboardsDto getFreeboardsDto = new GetFreeboardsDto();
            getFreeboardsDto.setId(freeBoard.getId());
            getFreeboardsDto.setTitle(freeBoard.getTitle());
            getFreeboardsDto.setCreatDate(freeBoard.getCreatDate());
            freeboardsDtos.add(getFreeboardsDto);
        }

        return freeboardsDtos;
    }
}
