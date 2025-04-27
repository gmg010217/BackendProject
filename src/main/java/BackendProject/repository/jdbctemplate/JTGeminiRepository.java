package BackendProject.repository.jdbctemplate;

import BackendProject.domain.ChatMessage;
import BackendProject.domain.CounselBoard;
import BackendProject.repository.GeminiRepository;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JTGeminiRepository implements GeminiRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTGeminiRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("chat_message")
                .usingGeneratedKeyColumns("id")
                .usingColumns("writer_id", "sender", "content");
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(chatMessage);
        Number key = jdbcInsert.executeAndReturnKey(params);
        chatMessage.setId(key.longValue());
        return chatMessage;
    }

    @Override
    public List<ChatMessage> findAll(Long memberId) {
        String sql = "select * from chat_message where writer_id = :id order by create_at asc";
        Map<String, Object> param = Map.of("id", memberId);
        try {
            return jdbcTemplate.query(sql, param, geminiRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from chat_message where id = :id";
        Map<String, Object> param = Map.of("id", id);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public LocalDateTime findCreateDateById(Long id) {
        String sql = "select * from chat_message where id = :id";
        Map<String, Object> param = Map.of("id", id);

        try {
            return jdbcTemplate.queryForObject(sql, param, (rs, rowNum) ->
                    rs.getTimestamp("create_at").toLocalDateTime()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<ChatMessage> geminiRowMapper() {
        return BeanPropertyRowMapper.newInstance(ChatMessage.class);
    }
}
