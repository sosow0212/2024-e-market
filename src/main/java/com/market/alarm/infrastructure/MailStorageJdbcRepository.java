package com.market.alarm.infrastructure;

import com.market.alarm.domain.MailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MailStorageJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void deleteAllByMailStatus(final MailStatus mailStatus) {
        String sql = "DELETE FROM mail_storage WHERE mail_status = :mailStatus";

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("mailStatus", mailStatus.name()));
    }
}
