package com.damintsev.server.old;

import com.damintsev.client.old.devices.Device;
import com.damintsev.client.old.devices.graph.BusyInfo;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 18.08.13
 * Time: 22:23
 */
public class HibernateProxy {

    public static void saveBusyInfo(BusyInfo info) {
        Session session = Hibernate.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(info);
        session.getTransaction().commit();
    }

    public static List<BusyInfo> loadBusyInfo(Device device) {
        Session session = Hibernate.getSessionFactory().openSession();
       Query query = session.createQuery("SELECT i FROM BusyInfo i " +
                "WHERE i.deviceId = :id " +
               " AND i.date >= :date");
        query.setLong("id", device.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        query.setDate("date", calendar.getTime());
        return query.list();
    }
}
