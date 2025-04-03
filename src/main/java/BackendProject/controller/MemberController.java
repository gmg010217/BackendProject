package BackendProject.controller;

import BackendProject.domain.Member;
import BackendProject.dto.LoginDto;
import BackendProject.dto.MemberInfoDto;
import BackendProject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthmind/member/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody Member member) {
        memberService.signUp(member);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request) {
        Member loginMember = memberService.login(loginDto.getEmailId(), loginDto.getPassword());
        loginDto.setId(loginMember.getId());

        if (loginMember == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("아이디와 비밀번호가 일치하지 않습니다");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginDto);
    }

    @GetMapping("info/{id}")
    public ResponseEntity<?> info(@PathVariable Long id) {
        Member member = memberService.info(id);

        if (member == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("존재하지 않는 회원입니다");
        } else {
            MemberInfoDto memberInfoDto = new MemberInfoDto();
            memberInfoDto.setNickname(member.getNickName());
            memberInfoDto.setEmailId(member.getEmailId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(memberInfoDto);
        }
    }
}