package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Exercise;
import BackendProject.repository.ExerciseRepository;
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
public class JTExerciseRepository implements ExerciseRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTExerciseRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("exercises")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Exercise save(Exercise exercise) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(exercise);
        Number key = jdbcInsert.executeAndReturnKey(param);
        exercise.setId(key.longValue());
        System.out.println("exercise = " + exercise);
        return exercise;
    }

    @Override
    public Exercise findByDate(Long memberId, LocalDate exerciseDate) {
        String sql = "select * from exercises where member_id = :id AND exercise_date = :date";
        try {
            Map<String, Object> param = Map.of("id", memberId, "date", exerciseDate);
            return jdbcTemplate.queryForObject(sql, param, exerciseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Exercise edit(Long memberId, LocalDate date, Exercise exercise) {
        exercise.setExerciseDate(date);
        String sql = "update exercises set title = :title, content = :content where member_id = "
                + memberId + " AND exercise_date = :exerciseDate";
        SqlParameterSource param = new BeanPropertySqlParameterSource(exercise);
        int updateMember = jdbcTemplate.update(sql, param);

        if (updateMember == 1) {
            return findByDate(memberId, exercise.getExerciseDate());
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long memberId, LocalDate exerciseDate) {
        String sql = "select * from exercises";
        try {
            jdbcTemplate.query(sql, exerciseRowMapper());
        } catch (EmptyResultDataAccessException e) {
        }
    }

    @Override
    public List<Exercise> findAll(Long memberId) {
        String sql = "select * from exercises where member_id = :id";
        try {
            Map<String, Object> param = Map.of("id", memberId);
            return jdbcTemplate.query(sql, param, exerciseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<Exercise> exerciseRowMapper() {
        return BeanPropertyRowMapper.newInstance(Exercise.class);
    }
}
