package BackendProject.controller;

import BackendProject.dto.memberdto.JoinMemberDto;
import BackendProject.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public void join(@RequestBody JoinMemberDto joinMemberDto) {
        memberService.join(joinMemberDto);
    }
}