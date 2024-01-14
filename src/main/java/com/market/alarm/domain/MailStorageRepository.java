package com.market.alarm.domain;

import java.util.List;

public interface MailStorageRepository {

    void save(final MailStorage mailStorage);

    List<MailStorage> findAllByNotDone();

    void deleteAllByDoneMails();
}
