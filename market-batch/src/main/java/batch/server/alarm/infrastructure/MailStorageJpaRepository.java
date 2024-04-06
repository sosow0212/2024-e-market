package batch.server.alarm.infrastructure;

import batch.server.alarm.domain.mail.vo.MailStatus;
import batch.server.alarm.domain.mail.MailStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailStorageJpaRepository extends JpaRepository<MailStorage, Long> {

    MailStorage save(final MailStorage mailStorage);

    List<MailStorage> findAllByMailStatus(final MailStatus mailStatus);
}
