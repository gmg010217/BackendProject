package BackendProject.service;

import BackendProject.domain.FreeBoard;
import BackendProject.domain.FreeComment;
import BackendProject.dto.freeboard.AddFreeboardDto;
import BackendProject.dto.freeboard.GetFreeCommentDto;
import BackendProject.dto.freeboard.GetFreeboardDto;
import BackendProject.dto.freeboard.GetFreeboardsDto;
import BackendProject.repository.FreeBoardRepository;
import BackendProject.repository.FreeCommentRepository;
import BackendProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    private final MemberRepository memberRepository;

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

    public List<GetFreeboardsDto> searchByTitle(String title) {
        List<FreeBoard> freeBoards = freeBoardRepository.findByTitle(title);
        for (FreeBoard freeBoard : freeBoards) {
            LocalDate date = freeBoardRepository.findCreateDateById(freeBoard.getId());
            freeBoard.setCreatDate(date);
        }
        List<GetFreeboardsDto> result = new ArrayList<>();

        for (FreeBoard freeBoard : freeBoards) {
            GetFreeboardsDto freeBoardDto = new GetFreeboardsDto();
            freeBoardDto.setId(freeBoard.getId());
            freeBoardDto.setTitle(freeBoard.getTitle());
            freeBoardDto.setCreatDate(freeBoard.getCreatDate());
            result.add(freeBoardDto);
        }

        return result;
    }

    public GetFreeboardDto getFreeBoard(Long memberId, Long boardId) {
        FreeBoard freeboard = freeBoardRepository.findById(boardId);
        GetFreeboardDto freeboardDto = new GetFreeboardDto();

        if (freeboard != null) {
            freeboardDto.setWriterId(freeboard.getWriterId());
            freeboardDto.setTitle(freeboard.getTitle());
            freeboardDto.setContent(freeboard.getContent());
            freeboardDto.setWriterName(memberRepository.findById(freeboard.getWriterId()).getNickName());

            List<GetFreeCommentDto> comments = new ArrayList<>();
            for (FreeComment comment : freeCommentRepository.getBoardComment(boardId)) {
                GetFreeCommentDto commentDto = new GetFreeCommentDto();
                commentDto.setWriterId(comment.getWriterId());
                commentDto.setContent(comment.getContent());
                commentDto.setWriterName(memberRepository.findById(comment.getWriterId()).getNickName());
                comments.add(commentDto);
            }
            freeboardDto.setComments(comments);
        }

        return freeboardDto;
    }

    public void addComment(Long memberId, Long boardid, String comment) {
        FreeComment freeComment = new FreeComment();
        freeComment.setWriterId(memberId);
        freeComment.setBoardId(boardid);
        freeComment.setContent(comment);
        freeCommentRepository.save(freeComment);
    }

    public String deleteFreeBoard(Long memberId, Long boardId) {
        FreeBoard freeBoardId = freeBoardRepository.findById(boardId);

        if (memberId.equals(freeBoardId.getWriterId())) {
            freeBoardRepository.delete(boardId);
            return "success";
        } else {
            return "fail";
        }
    }

    public String editFreeBoard(Long memberId, Long boardId, AddFreeboardDto addFreeboardDto) {
        FreeBoard freeBoardId = freeBoardRepository.findById(boardId);
        freeBoardId.setTitle(addFreeboardDto.getTitle());
        freeBoardId.setContent(addFreeboardDto.getContent());

        if (memberId.equals(freeBoardId.getWriterId())) {
            freeBoardRepository.edit(freeBoardId);
            return "success";
        } else {
            return "fail";
        }
    }

    public AddFreeboardDto getEditFreeBoard(Long memberId, Long boardId) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardId);

        if (freeBoard.getWriterId() != memberId) {
            return null;
        }

        AddFreeboardDto addFreeboardDto = new AddFreeboardDto();
        addFreeboardDto.setTitle(freeBoard.getTitle());
        addFreeboardDto.setContent(freeBoard.getContent());
        return addFreeboardDto;
    }

    public String deleteComment(Long memberId, Long commentId) {
        FreeComment freeComment = freeCommentRepository.findById(commentId);

        if (memberId.equals(freeComment.getWriterId())) {
            freeCommentRepository.delete(commentId);
            return "success";
        } else {
            return "fail";
        }
    }
}
