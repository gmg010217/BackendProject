package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Member;
import BackendProject.dto.memberdto.JoinMemberDto;
import BackendProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JTMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JTMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");;
    }

    @Override
    public Member save(JoinMemberDto joinMemberDto) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(joinMemberDto);
        Number key = jdbcInsert.executeAndReturnKey(param);

        Member member = new Member();
        member.setId(key.longValue());
        member.setAge(joinMemberDto.getAge());
        member.setGender(joinMemberDto.getGender());
        member.setNickname(joinMemberDto.getNickname());
        member.setPassword(joinMemberDto.getPassword());
        member.setAboutMe(joinMemberDto.getAboutMe());
        member.setEmailId(joinMemberDto.getEmailId());

        return member;
    }
}