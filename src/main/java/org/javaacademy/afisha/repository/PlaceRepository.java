package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.afisha.entity.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Place> findById(Long id) {
        String sql = """
                select * from place where id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::rowMapper, id));
    }

    public List<Place> findAll() {
        String sql = """
                select * from place
                """;
        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public void save(Place place) {
        String sql = """
                insert into place (name, address, city) values(?, ?, ?) returning id
                """;
        Long id = jdbcTemplate.queryForObject(sql, Long.class, place.getName(), place.getAddress(), place.getCity());
        place.setId(id);
    }

    @SneakyThrows
    private Place rowMapper(ResultSet resultSet, int rownumber) {
        return Place.builder()
                .id(resultSet.getLong("id"))
                .city(resultSet.getString("city"))
                .name(resultSet.getString("name"))
                .address(resultSet.getString("address"))
                .build();
    }

}
