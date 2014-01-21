package com.damintsev.server.v2.v3;

import com.damintsev.server.dao.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
@Component
public class Executor2 {

    @Autowired
    private DataBase dataBase;

    @PostConstruct
    public void init() {
        List aaa = dataBase.getStationList();
        System.err.println("aaaaaaaaaaa=" + aaa.size());
    }
}
