package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Report> getReports() {
        String sql = """
                   select e.name as event_name, et.name as event_type_name,
                   	count(t.*) qty_selled_tickets, sum(coalesce(t.price,0)) as sum_price
                   from event e
                   	left join event_type et on e.event_type_id = et.id
                   	left join (select * from ticket where is_selled = true) t on t.event_id = e.id
                   group by e.name, et.name
                """;
        return jdbcTemplate.query(sql, this::convertToEntity);
    }

    @SneakyThrows
    private Report convertToEntity(ResultSet rs, int rowNumber) {
        return Report.builder()
                .eventName(rs.getString("event_name"))
                .eventTypeName(rs.getString("event_type_name"))
                .countSelledTickets(rs.getInt("qty_selled_tickets"))
                .sumTicketPrices(rs.getBigDecimal("sum_price"))
                .build();
    }
}
