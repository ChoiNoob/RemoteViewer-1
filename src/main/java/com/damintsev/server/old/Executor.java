package com.damintsev.server.old;


import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by adamintsev
 * Date: 28.08.13 15:51
 */
public class Executor {

    private static final Logger logger = Logger.getLogger(Executor.class);

    private static Executor instance;

    public SingleThread createThread(Class clazz) {
        SingleThread instance = new SingleThread();
        executors.put(clazz.getName(), instance);
        return instance;
    }

    public static Executor getInstance() {
        if(instance ==null) instance = new Executor();
        return instance;
    }

    private Executor() {
        executors = new HashMap<String, SingleThread>();
    }

    private  Map<String, SingleThread> executors ;

//    public ScheduledExecutorService getScheduler() {
//        return getScheduler("Thread" + schedulers.size());
//    }
//
//    public ScheduledExecutorService getScheduler(String name) {
//        if(schedulers.getInstance(name) != null)
//            return schedulers.getInstance(name);
//        ScheduledExecutorService sched = Executors.newSingleThreadScheduledExecutor();
//        schedulers.put(name, sched);
//        return sched;
//    }

    public void shutdownAll() {
        for(SingleThread thread : executors.values()) {
            logger.info("Stopping thread " + thread.toString());
            thread.shutdown();
        }
    }

    public void scheduleAtFixedRate(Runnable runnable, int i, int i1, TimeUnit seconds) {
        for(int z = 0; z < 5; z++)
            logger.info("CPT:" + z + ":" + Thread.currentThread().getStackTrace()[z].getClassName());
    }
}
