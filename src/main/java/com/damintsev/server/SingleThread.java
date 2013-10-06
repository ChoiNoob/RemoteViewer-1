package com.damintsev.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 15:18
 */
public class SingleThread {

    private ScheduledExecutorService sched;

    public SingleThread() {
//        sched = Executors.newSingleThreadScheduledExecutor();
    }

    public void scheduleAtFixedRate(Runnable command,
                                    long initialDelay,
                                    long period,
                                    TimeUnit unit) {
        if(sched == null || sched.isShutdown() || sched.isTerminated()) {
            sched = Executors.newSingleThreadScheduledExecutor();
            sched.scheduleAtFixedRate(command, initialDelay, period, unit);
        }
    }

    public void shutdown() {
        sched.shutdown();
    }
}
