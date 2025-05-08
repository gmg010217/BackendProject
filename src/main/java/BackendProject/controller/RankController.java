package BackendProject.controller;

import BackendProject.domain.Rank;
import BackendProject.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/healthmind/rank/")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping("{id}")
    public ResponseEntity<?> getRank(@PathVariable("id") Long memberId) {
        List<Rank> ranks = rankService.getRank(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ranks);
    }
}