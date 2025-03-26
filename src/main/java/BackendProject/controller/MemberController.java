package BackendProject.controller;

import BackendProject.domain.Member;
import BackendProject.dto.memberdto.JoinMemberDto;
import BackendProject.dto.memberdto.LoginMemberDto;
import BackendProject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("health/")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("join")
    public Member join(@RequestBody JoinMemberDto joinMemberDto) {
        return memberService.join(joinMemberDto);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginMemberDto loginMemberDto, HttpServletRequest request) {
        Member login = memberService.login(loginMemberDto);

        if (login == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 회원이거나 아이디 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", login);

        return ResponseEntity.status(HttpStatus.OK).body("로그인에 성공하였습니다");
    }
}