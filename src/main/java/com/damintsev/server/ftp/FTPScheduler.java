package com.damintsev.server.ftp;

import org.slf4j.LoggerFactory;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 2:31
 */
public class FTPScheduler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FTPScheduler.class);
    private static FTPScheduler instance;

    public static FTPScheduler getInstance() {
        if(instance==null) instance = new FTPScheduler();
        return instance;
    }

    private FTPScheduler() {

    }
}
