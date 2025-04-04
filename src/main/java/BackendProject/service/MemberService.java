package BackendProject.service;

import BackendProject.domain.Member;
import BackendProject.dto.MemberEditInfoDto;
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

    public Member info(Long id) {
        return memberRepository.findById(id);
    }

    public MemberEditInfoDto editInfo(Long id) {
        Member member = memberRepository.findById(id);

        MemberEditInfoDto memberEditInfoDto = new MemberEditInfoDto();
        memberEditInfoDto.setNickName(member.getNickName());
        memberEditInfoDto.setAge(member.getAge());
        memberEditInfoDto.setGender(member.getGender());
        memberEditInfoDto.setAboutMe(member.getAboutMe());

        return memberEditInfoDto;
    }

    public Member edit(Long id, MemberEditInfoDto memberEditInfoDto) {
        return memberRepository.edit(id, memberEditInfoDto);
    }
}