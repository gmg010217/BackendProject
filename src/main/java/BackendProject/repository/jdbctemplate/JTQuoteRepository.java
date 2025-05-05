package BackendProject.repository.jdbctemplate;

import BackendProject.domain.Quote;
import BackendProject.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.Map;

@Repository
public class JTQuoteRepository implements QuoteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JTQuoteRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Quote findBy(Long quoteId) {
        System.out.println("quoteId = " + quoteId);
        String sql = "select * from quotes where qday = :qday";
        try {
            Map<String, Object> params = Map.of("qday", quoteId.intValue());
            Quote quote = jdbcTemplate.queryForObject(sql, params, quoteRowMapper());
            return quote;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("no..");
            return null;
        }
    }

    private RowMapper<Quote> quoteRowMapper() {
        return (rs, rowNum) -> {
            Quote q = new Quote();
            q.setQday(rs.getInt("qday"));
            q.setContent(rs.getString("content"));
            return q;
        };
    }
}