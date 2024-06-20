package org.javaacademy.afisha;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"classpath:/schema-test.sql", "classpath:/data-test.sql"})
public abstract class AbstractIntegrationTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    protected Long placeId;
    protected Long theatreEventTypeId;

    @PostConstruct
    public void init() {
        placeId = jdbcTemplate.queryForObject("select id from place limit 1", Long.class);
        theatreEventTypeId = jdbcTemplate.queryForObject("select id from event_type where name = 'theatre' limit 1", Long.class);
    }

}
