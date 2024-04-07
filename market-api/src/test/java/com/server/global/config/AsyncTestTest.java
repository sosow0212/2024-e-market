package com.server.global.config;

import com.server.helper.IntegrationHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AsyncTestTest extends IntegrationHelper {

//    @Autowired
//    private AsyncTest asyncTest;

    //    @Test
    void async_test_thread_poll_counter() throws InterruptedException {
        // given
        int thread = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch latch = new CountDownLatch(thread);

        // when
        for (int i = 0; i < thread; i++) {
            executorService.submit(() -> {
                try {
//                    asyncTest.call();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }
}
