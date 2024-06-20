package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Ticket> findById(Long id) {
        String sql = """
                select * from ticket where id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::rowMapper, id));
    }

    public List<Ticket> findAll() {
        String sql = """
                select * from ticket
                """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public void save(Ticket ticket) {
        String sql = """
                insert into ticket (price, is_selled, client_email, event_id) values(?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql, ticket.getPrice(), ticket.getIsSelled(), ticket.getClientEmail(), ticket.getEventId());
    }

    public void createTickets(BigDecimal price, Long eventId, int countTickets) {
        String sql = """
                insert into ticket (price, is_selled, event_id) values(?, false, ?)
                """;
        List<Object[]> arguments = IntStream.range(0, countTickets)
                .boxed()
                .map(e -> new Object[]{price, eventId})
                .toList();
        jdbcTemplate.batchUpdate(sql, arguments);
    }

    public void createSelledTicket(BigDecimal price, Long eventId, String email) {
        String sql = """
                insert into ticket (price, is_selled, event_id, email) values(?, true, ?, ?)
                """;
        jdbcTemplate.update(sql, price, eventId, email);
    }

    @SneakyThrows
    private Ticket rowMapper(ResultSet resultSet, int rowNumber) {
        return Ticket.builder()
                .id(resultSet.getLong("id"))
                .price(resultSet.getBigDecimal("price"))
                .isSelled(resultSet.getBoolean("is_selled"))
                .clientEmail(resultSet.getString("client_email"))
                .eventId(resultSet.getLong("event_id"))
                .build();
    }

    public Optional<Ticket> findFreeTicketByEventId(Long eventId) {
        String sql = """
                select * from ticket where (is_selled = false or is_selled is null) and event_id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::rowMapper, eventId));
    }

    public void buyTicket(Ticket ticket, String email) {
        String sql = """
                update ticket set client_email = ?, price = ?, is_selled = true where id = ?
                """;
        jdbcTemplate.update(sql, email, ticket.getPrice(), ticket.getId());
    }
}
