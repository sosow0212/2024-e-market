package batch.server.alarm.infrastructure;

import batch.server.alarm.domain.MailStatus;
import batch.server.alarm.domain.MailStorage;
import batch.server.alarm.domain.MailStorageRepository;
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
