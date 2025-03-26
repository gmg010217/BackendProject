package BackendProject.repository;

import BackendProject.domain.Member;
import BackendProject.dto.memberdto.JoinMemberDto;

public interface MemberRepository {
    Member save(JoinMemberDto joinMemberDto);
}
