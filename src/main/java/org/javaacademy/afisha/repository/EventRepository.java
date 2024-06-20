package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Event event) {
        String sql = """
                    insert into event (name, event_type_id, event_date, place_id) values 
                    (?, ?, ?, ?) returning id
                """;
        Long id = jdbcTemplate.queryForObject(sql, Long.class, event.getName(),
                event.getEventTypeId(), event.getEventDate(), event.getPlaceId());
        event.setId(id);
    }

    public Optional<Event> findById(Long id) {
        String sql = """
                select * from event where id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::rowMapper, id));
    }

    public List<Event> findAll() {
        String sql = """
                select * from event
                """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    @SneakyThrows
    private Event rowMapper(ResultSet resultSet, int rowNumber) {
        return Event.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .placeId(resultSet.getLong("place_id"))
                .eventDate(resultSet.getTimestamp("event_date").toLocalDateTime())
                .eventTypeId(resultSet.getLong("event_type_id"))
                .build();
    }

}
