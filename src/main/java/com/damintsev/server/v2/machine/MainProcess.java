package com.damintsev.server.v2.machine;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:11
 */
public class MainProcess {

    private static MainProcess instance;

    public static MainProcess getInstance() {
        if(instance==null)instance=new MainProcess();
        return instance;
    }

    private MainProcess() {

    }
}

