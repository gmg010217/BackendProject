package BackendProject.repository;

import BackendProject.domain.Member;

import java.util.List;
public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    Member findByEmailId(String email);
    List<Member> findAll();
}
