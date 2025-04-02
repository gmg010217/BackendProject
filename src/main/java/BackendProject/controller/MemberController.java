package BackendProject.controller;

import BackendProject.domain.Member;
import BackendProject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthmind/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody Member member) {
        System.out.println("aa");
        memberService.signUp(member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }
}