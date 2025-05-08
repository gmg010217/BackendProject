package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Quiz;
import BackendProject.dto.QuizSummaryDto;
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
import java.time.LocalDate;
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
    public List<QuizSummaryDto> findAll() {
        String sql = "select member_id, sum(correct_count) as total_correct_count " +
                "from quiz group by member_id order by total_correct_count DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            QuizSummaryDto quizSummaryDto = new QuizSummaryDto();
            quizSummaryDto.setMemberId(rs.getLong("member_id"));
            quizSummaryDto.setTotalCorrectCount(rs.getInt("total_correct_count"));
            return quizSummaryDto;
        });
    }

    @Override
    public boolean existsByMemberIdAndDate(Long memberId, LocalDate date) {
        String sql = "select COUNT(*) from quiz where member_id = :memberId " +
                "and date(quiz_date) = :date";

        Map<String, Object> params = Map.of(
                "memberId", memberId,
                "date", date
        );

        try {
            int count = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
