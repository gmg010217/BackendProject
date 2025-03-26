package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.dto.memberdto.JoinMemberDto;
import BackendProject.dto.memberdto.LoginMemberDto;
import BackendProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(JoinMemberDto joinMemberDto) {
        return memberRepository.save(joinMemberDto);
    }

    public Member login(LoginMemberDto loginMemberDto) {
        Member findMember = memberRepository.findByEmail(loginMemberDto.getEmailId());

        if (findMember == null) {
            return null;
        }

        if (!findMember.getPassword().equals(loginMemberDto.getPassword())) {
            return null;
        }

        return findMember;
    }
}