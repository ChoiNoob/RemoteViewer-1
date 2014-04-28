package com.damintsev.client.v3.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 11.10.13
 * Time: 0:25
 * @Depricated
 * //todo убить нахер!
 */
public class Scheduler {

    private static Scheduler instance;

    public static Scheduler getInstance() {
        if (instance == null) instance = new Scheduler();
        return instance;
    }

    private Map<String, Boolean> continuePointer;

    private Scheduler() {
        continuePointer = new HashMap<String, Boolean>();
    }

    public void start(String name, Runnable run) {
        start(name, run, 5000);
    }

    public void start(final String name, final Runnable run, int repeating) {
        continuePointer.put(name, true);

        com.google.gwt.core.client.Scheduler.get().scheduleFixedPeriod(new com.google.gwt.core.client.Scheduler.RepeatingCommand() {
            public boolean execute() {
                run.run();
                return continuePointer.get(name);
            }
        }, repeating);
    }

    public void stop(String name) {
        continuePointer.put(name, false);
    }
}
