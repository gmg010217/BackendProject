package BackendProject.repository.jdbctemplate;

import BackendProject.domain.CounselBoard;
import BackendProject.domain.CounselComment;
import BackendProject.repository.CounselCommentRepository;
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
public class JTCounselCommentRepository implements CounselCommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTCounselCommentRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("counsel_comment")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public CounselComment save(CounselComment counselComment) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(counselComment);
        Number key = jdbcInsert.executeAndReturnKey(params);
        counselComment.setId(key.longValue());
        return counselComment;
    }

    @Override
    public CounselComment findById(Long commentId) {
        String sql = "select * from counsel_comment where id = :id";
        try {
            Map<String, Object> param = Map.of("id", commentId);
            return jdbcTemplate.queryForObject(sql, param, counselCommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Long commentId) {
        String sql = "delete from counsel_comment where id = :id";
        Map<String, Object> param = Map.of("id", commentId);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<CounselComment> getBoardComment(Long boardId) {
        String sql = "select * from counsel_comment where board_id = :boardId";
        try {
            Map<String, Object> param = Map.of("boardId", boardId);
            return jdbcTemplate.query(sql, param, counselCommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<CounselComment> counselCommentRowMapper() {
        return BeanPropertyRowMapper.newInstance(CounselComment.class);
    }
}