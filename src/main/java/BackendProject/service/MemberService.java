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

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmailId(email);
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
    }
}