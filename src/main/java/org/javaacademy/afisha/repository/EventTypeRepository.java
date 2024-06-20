package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.EventType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(EventType eventType) {
        String sql = "insert into event_type (name) values (?)";
        jdbcTemplate.update(sql, eventType.getName());
    }

    public Optional<EventType> findById(Long id) {
        String sql = """
                select * from event_type where id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::rowMapper, id));
    }

    public List<EventType> findAll() {
        String sql = """
                select * from event_type
                """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    @SneakyThrows
    public EventType rowMapper(ResultSet resultSet, int rowNumber) {
        return EventType.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
