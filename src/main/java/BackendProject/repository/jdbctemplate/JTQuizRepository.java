package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Quiz;
import BackendProject.repository.QuizRepository;
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
import java.util.List;
import java.util.Map;

@Repository
public class JTQuizRepository implements QuizRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTQuizRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("quiz")
                .usingGeneratedKeyColumns("id")
                .usingColumns("member_id", "correct_count");
    }

    @Override
    public Quiz save(Quiz quiz) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(quiz);
        Number key = jdbcInsert.executeAndReturnKey(params);
        quiz.setId(key.longValue());
        return quiz;
    }

    @Override
    public List<Quiz> findAll(Long memberId) {
        String sql = "select * from quiz where member_id = :memberId";
        try {
            Map<String, Object> param = Map.of("memberId", memberId);
            return jdbcTemplate.query(sql, freeBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<Quiz> freeBoardRowMapper() {
        return BeanPropertyRowMapper.newInstance(Quiz.class);
    }
}
