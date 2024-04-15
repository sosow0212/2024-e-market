package com.server.helper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ConcurrencyHelper {

    private static final int THREAD = 10;
    private static final int THREAD_POOL = 5;

    public static int execute(final Executable executable, final AtomicLong successCount)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD);
        CountDownLatch latch = new CountDownLatch(THREAD_POOL);

        for (long i = 0; i < THREAD; i++) {
            executorService.submit(() -> {
                try {
                    executable.execute();
                    successCount.getAndIncrement();
                } catch (final Throwable e) {
                    log.info(e.getClass().getName());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        return THREAD_POOL;
    }
}
