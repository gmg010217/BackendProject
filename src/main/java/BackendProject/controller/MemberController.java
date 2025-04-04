package BackendProject.controller;

import BackendProject.domain.Member;
import BackendProject.dto.LoginDto;
import BackendProject.dto.MemberEditInfoDto;
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
            return noMemberResponseEntity();
        } else {
            MemberInfoDto memberInfoDto = new MemberInfoDto();
            memberInfoDto.setNickName(member.getNickName());
            memberInfoDto.setEmailId(member.getEmailId());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(memberInfoDto);
        }
    }

    @GetMapping("edit/{id}")
    public ResponseEntity<?> editInfo(@PathVariable Long id) {
        MemberEditInfoDto member = memberService.editInfo(id);

        if (member == null) {
            return noMemberResponseEntity();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(member);
        }
    }

    @PostMapping("edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Validated @RequestBody MemberEditInfoDto memberEditInfoDto) {
        Member member = memberService.edit(id, memberEditInfoDto);

        if (member == null) {
            return noMemberResponseEntity();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("OK");
        }
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("OK");
    }

    private static ResponseEntity<String> noMemberResponseEntity() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("존재하지 않는 회원입니다");
    }
}