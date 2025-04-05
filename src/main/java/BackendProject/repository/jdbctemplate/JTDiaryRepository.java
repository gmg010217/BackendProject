package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Diary;
import BackendProject.repository.DiaryRepository;
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
public class JTDiaryRepository implements DiaryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JTDiaryRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("diary")
                .usingGeneratedKeyColumns("id")
                .usingColumns("member_id", "title", "content");
    }

    @Override
    public Diary save(Diary diary) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(diary);
        Number key = jdbcInsert.executeAndReturnKey(param);
        diary.setId(key.longValue());
        return diary;
    }

    @Override
    public Diary findById(Long diaryId) {
        String sql = "select * from diary where id = :diaryId";
        try {
            Map<String, Object> param = Map.of("diaryId", diaryId);
            return jdbcTemplate.queryForObject(sql, param, diaryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Diary findById(Long memberId, Long diaryId) {
        String sql = "select * from diary where id = :diaryId AND member_id = :memberId";
        try {
            Map<String, Object> param = Map.of("memberId", memberId, "diaryId", diaryId);
            return jdbcTemplate.queryForObject(sql, param, diaryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Diary edit(Long diaryId, Diary diary) {
        String sql = "update diary set title = :title, content = :content where id = " + diaryId;
        SqlParameterSource param = new BeanPropertySqlParameterSource(diary);
        int updateDiary = jdbcTemplate.update(sql, param);

        if (updateDiary == 1) {
            return findById(diaryId);
        } else {
            return null;
        }
    }

    @Override
    public List<Diary> findAll(Long memberId) {
        String sql = "select * from diary where member_id = " + memberId;
        try {
            return jdbcTemplate.query(sql, diaryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Long diaryId) {
        String sql = "delete from diary where id = :diaryId";
         Map<String, Object> param = Map.of("diaryId", diaryId);
        int update = jdbcTemplate.update(sql, param);
    }

    private RowMapper<Diary> diaryRowMapper() {
        return BeanPropertyRowMapper.newInstance(Diary.class);
    }
}
