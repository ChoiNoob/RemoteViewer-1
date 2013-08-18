package com.damintsev.server.db;

import com.damintsev.client.devices.graph.BusyInfo;
import org.hibernate.classic.Session;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 2:01
 */
public class CleanManager {

    private static CleanManager instance;

    public static CleanManager getInstance() {
        if(instance == null) instance = new CleanManager();
        return instance;
    }

    private CleanManager() {
    }

    public void cleanBusyInfo() {
        Session session = Hibernate.getSessionFactory().openSession();
        org.hibernate.Query query = session.createQuery("SELECT i FROM BusyInfo i WHERE i.date < :date");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        query.setDate("date", calendar.getTime());
        List<BusyInfo> oldRecords = query.list();

        session.beginTransaction();
        for(BusyInfo info : oldRecords) {
            session.delete(info);
        }
        session.getTransaction().commit();
    }
}
