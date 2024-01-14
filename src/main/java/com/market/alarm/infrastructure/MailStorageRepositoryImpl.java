package com.market.alarm.infrastructure;

import com.market.alarm.domain.MailStatus;
import com.market.alarm.domain.MailStorage;
import com.market.alarm.domain.MailStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MailStorageRepositoryImpl implements MailStorageRepository {

    private final MailStorageJpaRepository mailStorageJpaRepository;
    private final MailStorageJdbcRepository mailStorageJdbcRepository;

    @Override
    public void save(final MailStorage mailStorage) {
        mailStorageJpaRepository.save(mailStorage);
    }

    @Override
    public List<MailStorage> findAllByNotDone() {
        return mailStorageJpaRepository.findAllByMailStatus(MailStatus.FAIL);
    }

    @Override
    public void deleteAllByDoneMails() {
        mailStorageJdbcRepository.deleteAllByMailStatus(MailStatus.DONE);
    }
}
