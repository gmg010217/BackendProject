package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(Member member) {
        return memberRepository.save(member);
    }
}