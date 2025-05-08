package BackendProject.service;

import BackendProject.domain.Rank;
import BackendProject.dto.QuizSummaryDto;
import BackendProject.repository.MemberRepository;
import BackendProject.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final QuizRepository quizRepository;
    private final MemberRepository memberRepository;

    public List<Rank> getRank(Long memberId) {
        List<QuizSummaryDto> all = quizRepository.findAll();
        List<Rank> result = new ArrayList<>();

        Long ranking = 1L;
        Rank myRank = null;

        for (QuizSummaryDto quizSummaryDto : all) {
            Rank rank = new Rank();
            rank.setNickName(memberRepository.findById(quizSummaryDto.getMemberId()).getNickName());
            Integer score = quizSummaryDto.getTotalCorrectCount() * 10;
            rank.setScore(score.toString());
            rank.setRanking(ranking.toString());

            if (memberId.equals(quizSummaryDto.getMemberId())) {
                myRank = rank;
            } else {
                result.add(rank);
            }

            ranking++;
        }

        if (myRank != null) {
            result.add(0, myRank);
        }

        return result;
    }
}
