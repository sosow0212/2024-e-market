package batch.server.alarm.domain.mail;

import java.util.List;

public interface MailStorageRepository {

    void save(final MailStorage mailStorage);

    List<MailStorage> findAllByNotDone();

    void deleteAllByDoneMails();
}
