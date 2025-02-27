package com.server.helper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)})
@SpringBootTest
public class JdbcTestHelper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDatabase() {
        validateH2Database();
        List<String> truncateQueries = getTruncateQueries();
        truncateAllTables(truncateQueries);
    }

    private void validateH2Database() {
        jdbcTemplate.queryForObject("SELECT H2VERSION() FROM DUAL", String.class);
    }

    private List<String> getTruncateQueries() {
        return jdbcTemplate.queryForList("SELECT CONCAT('TRUNCATE TABLE ', TABLE_NAME, ' RESTART IDENTITY', ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
    }

    private void truncateAllTables(List<String> truncateQueries) {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        for (String truncateQuery : truncateQueries) {
            jdbcTemplate.execute(truncateQuery);
        }

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}
