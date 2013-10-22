package com.damintsev.client.v3.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 11.10.13
 * Time: 0:25
 */
public class Scheduler {

    private static Scheduler instance;

    public static Scheduler getInstance() {
        if(instance==null)instance = new Scheduler();
        return instance;
    }

    private Map<String, Boolean> continuePointer;

    private Scheduler() {
        continuePointer = new HashMap<String, Boolean>();
    }

    public void start(Class clazz, Runnable run) {
        start(clazz, run, 5000);
    }

    public void start(final Class clazz, final Runnable run, int repeating) {
//        if(continuePointer.getInstance(clazz.getName()) )
//            continuePointer.put(clazz.getName(), false); //todo заврешим старый поток
        continuePointer.put(clazz.getName(), true);
        com.google.gwt.core.client.Scheduler.get().scheduleFixedPeriod(new com.google.gwt.core.client.Scheduler.RepeatingCommand() {
            public boolean execute() {
                run.run();
                return continuePointer.get(clazz.getName());
            }
        }, repeating);
    }

    public void stop(Class clazz) {
        continuePointer.put(clazz.getName(), false);
    }
}
