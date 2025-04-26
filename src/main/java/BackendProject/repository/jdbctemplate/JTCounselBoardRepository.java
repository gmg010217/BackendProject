package BackendProject.repository.jdbctemplate;

import BackendProject.domain.CounselBoard;
import BackendProject.repository.CounselBoardRepository;
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
public class JTCounselBoardRepository implements CounselBoardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTCounselBoardRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("counsel_board")
                .usingGeneratedKeyColumns("id")
                .usingColumns("writer_id", "title", "content");
    }

    @Override
    public CounselBoard save(CounselBoard counselBoard) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(counselBoard);
        Number key = jdbcInsert.executeAndReturnKey(params);
        counselBoard.setId(key.longValue());
        return counselBoard;
    }

    @Override
    public CounselBoard findById(Long boardId) {
        String sql = "select * from counsel_board where id = :id";
        try {
            Map<String, Object> param = Map.of("id", boardId);
            return jdbcTemplate.queryForObject(sql, param, counselBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public CounselBoard edit(CounselBoard counselBoard) {
        String sql = "update counsel_board set title = :title, content = :content where id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(counselBoard);
        int updateBoard = jdbcTemplate.update(sql, params);

        if (updateBoard == 1) {
            return findById(counselBoard.getId());
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long boardId) {
        String sql = "delete from counsel_board where id = :id";
        Map<String, Object> param = Map.of("id", boardId);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<CounselBoard> findByTitle(String title) {
        String sql = "select * from counsel_board where title like :title";
        Map<String, Object> param = Map.of("title", "%" + title + "%");
        return jdbcTemplate.query(sql, param, counselBoardRowMapper());
    }

    @Override
    public List<CounselBoard> findAll() {
        String sql = "select * from counsel_board";
        try {
            return jdbcTemplate.query(sql, counselBoardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public LocalDate findCreateDateById(Long id) {
        String sql = "select * from counsel_board where id = :id";
        Map<String, Object> param = Map.of("id", id);

        try {
            return jdbcTemplate.queryForObject(sql, param, (rs, rowNum) ->
                    rs.getTimestamp("create_date").toLocalDateTime().toLocalDate()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<CounselBoard> counselBoardRowMapper() {
        return BeanPropertyRowMapper.newInstance(CounselBoard.class);
    }
}