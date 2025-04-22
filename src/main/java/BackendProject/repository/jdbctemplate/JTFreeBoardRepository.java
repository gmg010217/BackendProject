package BackendProject.repository.jdbctemplate;

import BackendProject.domain.FreeBoard;
import BackendProject.repository.FreeBoardRepository;
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
public class JTFreeBoardRepository implements FreeBoardRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTFreeBoardRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("free_board")
                .usingGeneratedKeyColumns("id")
                .usingColumns("writer_id", "title", "content");
    }

    @Override
    public FreeBoard save(FreeBoard freeBoard) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(freeBoard);
        Number key = jdbcInsert.executeAndReturnKey(params);
        freeBoard.setId(key.longValue());
        return freeBoard;
    }

    @Override
    public FreeBoard findById(Long boardId) {
        String sql = "select * from free_board where id = :id";
        try {
            Map<String, Object> param = Map.of("id", boardId);
            return jdbcTemplate.queryForObject(sql, param, freeBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public FreeBoard edit(FreeBoard freeBoard) {
        String sql = "update free_board set title = :title, content = :content where id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(freeBoard);
        int updateBoard = jdbcTemplate.update(sql, params);

        if (updateBoard == 1) {
            return findById(freeBoard.getId());
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long boardId) {
        String sql = "delete from free_board where id = :id";
        Map<String, Object> param = Map.of("id", boardId);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<FreeBoard> findByTitle(String title) {
        String sql = "select * from free_board where title like :title";
        Map<String, Object> param = Map.of("title", title);
        return jdbcTemplate.query(sql, param, freeBoardRowMapper());
    }

    @Override
    public List<FreeBoard> findAll() {
        String sql = "select * from free_board";
        try {
            return jdbcTemplate.query(sql, freeBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public LocalDate findCreateDateById(Long id) {
        String sql = "select * from free_board where id = :id";
        Map<String, Object> param = Map.of("id", id);

        try {
            return jdbcTemplate.queryForObject(sql, param, (rs, rowNum) ->
                    rs.getTimestamp("create_date").toLocalDateTime().toLocalDate()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<FreeBoard> freeBoardRowMapper() {
        return BeanPropertyRowMapper.newInstance(FreeBoard.class);
    }
}