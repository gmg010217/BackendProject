package BackendProject.service;

import BackendProject.domain.CounselBoard;
import BackendProject.domain.CounselComment;
import BackendProject.dto.counselboard.AddCounselboardDto;
import BackendProject.dto.counselboard.GetCounselCommentDto;
import BackendProject.dto.counselboard.GetCounselboardDto;
import BackendProject.dto.counselboard.GetCounselboardsDto;
import BackendProject.repository.CounselBoardRepository;
import BackendProject.repository.CounselCommentRepository;
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
public class CounselBoardService {

    private final CounselBoardRepository counselBoardRepository;
    private final CounselCommentRepository counselCommentRepository;
    private final MemberRepository memberRepository;

    public CounselBoard addCounselBoard(Long memberId, AddCounselboardDto addCounselboardDto) {
        CounselBoard counselBoard = new CounselBoard();
        counselBoard.setWriterId(memberId);
        counselBoard.setTitle(addCounselboardDto.getTitle());
        counselBoard.setContent(addCounselboardDto.getContent());
        return counselBoardRepository.save(counselBoard);
    }

    public List<GetCounselboardsDto> getCounselBoards() {
        List<CounselBoard> counselBoards = counselBoardRepository.findAll();
        for (CounselBoard counselBoard : counselBoards) {
            LocalDate date = counselBoardRepository.findCreateDateById(counselBoard.getId());
            counselBoard.setCreatDate(date);
        }

        List<GetCounselboardsDto> counselboardsDtos = new ArrayList<>();
        for (CounselBoard counselBoard : counselBoards) {
            GetCounselboardsDto getCounselboardsDto = new GetCounselboardsDto();
            getCounselboardsDto.setId(counselBoard.getId());
            getCounselboardsDto.setTitle(counselBoard.getTitle());
            getCounselboardsDto.setCreatDate(counselBoard.getCreatDate());
            counselboardsDtos.add(getCounselboardsDto);
        }

        return counselboardsDtos;
    }

    public List<GetCounselboardsDto> searchByTitle(String title) {
        List<CounselBoard> counselBoards = counselBoardRepository.findByTitle(title);
        for (CounselBoard counselBoard : counselBoards) {
            LocalDate date = counselBoardRepository.findCreateDateById(counselBoard.getId());
            counselBoard.setCreatDate(date);
        }

        List<GetCounselboardsDto> result = new ArrayList<>();
        for (CounselBoard counselBoard : counselBoards) {
            GetCounselboardsDto counselboardDto = new GetCounselboardsDto();
            counselboardDto.setId(counselBoard.getId());
            counselboardDto.setTitle(counselBoard.getTitle());
            counselboardDto.setCreatDate(counselBoard.getCreatDate());
            result.add(counselboardDto);
        }

        return result;
    }

    public GetCounselboardDto getCounselBoard(Long memberId, Long boardId) {
        CounselBoard counselBoard = counselBoardRepository.findById(boardId);

        if (counselBoard != null) {
            GetCounselboardDto counselboardDto = new GetCounselboardDto();
            counselboardDto.setWriterId(counselBoard.getWriterId());
            counselboardDto.setTitle(counselBoard.getTitle());
            counselboardDto.setContent(counselBoard.getContent());
            counselboardDto.setWriterName(memberRepository.findById(counselBoard.getWriterId()).getNickName());

            List<GetCounselCommentDto> comments = new ArrayList<>();
            for (CounselComment comment : counselCommentRepository.getBoardComment(boardId)) {
                GetCounselCommentDto commentDto = new GetCounselCommentDto();
                commentDto.setId(comment.getId());
                commentDto.setWriterId(comment.getWriterId());
                commentDto.setContent(comment.getContent());
                commentDto.setWriterName(memberRepository.findById(comment.getWriterId()).getNickName());
                comments.add(commentDto);
            }
            counselboardDto.setComments(comments);

            return counselboardDto;
        }

        return null;
    }

    public CounselComment addComment(Long memberId, Long boardId, String comment) {
        CounselComment counselComment = new CounselComment();
        counselComment.setWriterId(memberId);
        counselComment.setBoardId(boardId);
        counselComment.setContent(comment);
        return counselCommentRepository.save(counselComment);
    }

    public String deleteCounselBoard(Long memberId, Long boardId) {
        CounselBoard counselBoard = counselBoardRepository.findById(boardId);

        if (memberId.equals(counselBoard.getWriterId())) {
            counselBoardRepository.delete(boardId);
            return "sucess";
        } else {
            return "fail";
        }
    }

    public String editCounselBoard(Long memberId, Long boardId, AddCounselboardDto addCounselboardDto) {
        CounselBoard counselBoard = counselBoardRepository.findById(boardId);
        counselBoard.setTitle(addCounselboardDto.getTitle());
        counselBoard.setContent(addCounselboardDto.getContent());

        if (memberId.equals(counselBoard.getWriterId())) {
            counselBoardRepository.edit(counselBoard);
            return "success";
        } else {
            return "fail";
        }
    }

    public AddCounselboardDto getEditCounselBoard(Long memberId, Long boardId) {
        CounselBoard counselBoard = counselBoardRepository.findById(boardId);

        if (!counselBoard.getWriterId().equals(memberId)) {
            return null;
        }

        AddCounselboardDto addCounselboardDto = new AddCounselboardDto();
        addCounselboardDto.setTitle(counselBoard.getTitle());
        addCounselboardDto.setContent(counselBoard.getContent());
        return addCounselboardDto;
    }

    public String deleteComment(Long memberId, Long commentId) {
        CounselComment counselComment = counselCommentRepository.findById(commentId);

        if (memberId.equals(counselComment.getWriterId())) {
            counselCommentRepository.delete(commentId);
            return "success";
        } else {
            return "fail";
        }
    }
}
