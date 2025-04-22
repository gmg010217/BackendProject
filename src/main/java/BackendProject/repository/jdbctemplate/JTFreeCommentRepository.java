package BackendProject.repository.jdbctemplate;

import BackendProject.domain.FreeComment;
import BackendProject.repository.FreeCommentRepository;
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
public class JTFreeCommentRepository implements FreeCommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTFreeCommentRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("free_comment")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public FreeComment save(FreeComment freeComment) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(freeComment);
        Number key = jdbcInsert.executeAndReturnKey(params);
        freeComment.setId(key.longValue());
        return freeComment;
    }

    @Override
    public FreeComment findById(Long commentId) {
        String sql = "select * from free_comment where id = :id";
        try {
            Map<String, Object> param = Map.of("id", commentId);
            return jdbcTemplate.queryForObject(sql, param, freeCommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Long commentId) {
        String sql = "delete from free_comment where id = :id";
        Map<String, Object> param = Map.of("id", commentId);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<FreeComment> getBoardComment(Long boardId) {
        String sql = "select * from free_comment where board_id = :boardId";
        try {
            Map<String, Object> param = Map.of("boardId", boardId);
            return jdbcTemplate.query(sql, param, freeCommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<FreeComment> freeCommentRowMapper() {
        return BeanPropertyRowMapper.newInstance(FreeComment.class);
    }
}