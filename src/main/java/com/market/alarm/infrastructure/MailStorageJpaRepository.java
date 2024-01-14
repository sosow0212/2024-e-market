package com.market.alarm.infrastructure;

import com.market.alarm.domain.MailStatus;
import com.market.alarm.domain.MailStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailStorageJpaRepository extends JpaRepository<MailStorage, Long> {

    MailStorage save(final MailStorage mailStorage);

    List<MailStorage> findAllByMailStatus(final MailStatus mailStatus);
}
