package BackendProject.repository;

import BackendProject.domain.Member;
import BackendProject.dto.MemberEditInfoDto;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    Member findByEmailId(String email);
    Member edit(Long id, MemberEditInfoDto member);
}
