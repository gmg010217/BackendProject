package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Member;
import BackendProject.dto.MemberEditInfoDto;
import BackendProject.repository.MemberRepository;
import BackendProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class JTMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Member save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Member findById(Long id) {
        String sql = "select * from member where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            return jdbcTemplate.queryForObject(sql, param, memberRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }

    @Override
    public Member findByEmailId(String email) {
        String sql = "select * from member where email_id = :email";
        try {
            Map<String, Object> param = Map.of("email", email);
            return jdbcTemplate.queryForObject(sql, param, memberRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Member edit(Long id, MemberEditInfoDto member) {
        String sql = "update member set nick_name = :nickName, age = :age, gender = :gender, about_me = :aboutMe where id = " + id;
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        int updateMember = jdbcTemplate.update(sql, param);

        if (updateMember == 1) {
            return findById(id);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from member where id = :id";
        Map<String, Object> param = Map.of("id", id);
        int update = jdbcTemplate.update(sql, param);
    }
}