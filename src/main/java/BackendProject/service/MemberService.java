package BackendProject.service;

import BackendProject.dto.memberdto.JoinMemberDto;
import BackendProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(JoinMemberDto joinMemberDto) {
        memberRepository.save(joinMemberDto);
    }
}