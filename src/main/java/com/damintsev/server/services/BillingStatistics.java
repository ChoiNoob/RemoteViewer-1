package com.damintsev.server.services;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.BillingStats;
import com.damintsev.server.db.xmldao.DatabaseConnector;
import com.damintsev.server.telnet.TelnetWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by adamintsev
 * Date: 20.08.13 12:37
 */
public class BillingStatistics {

    private static BillingStatistics instance;
    private static final Logger logger = LoggerFactory.getLogger(BillingStatistics.class);

    public static  BillingStatistics getInstance() {
        if(instance == null) instance = new BillingStatistics();
        return instance;
    }
    
    private Map<String,BillingStats> top;
    private List<BillingInfo> tmpBuffer;
    
    private BillingStatistics() {
        top = new TreeMap<String, BillingStats>();
        tmpBuffer = new ArrayList<BillingInfo>(30);
        loadFromDB();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clearCahche();
            }
        }, 1000 * 60 * 15, 1000 * 60 * 15);

        List<BillingInfo> billingInfoList = new ArrayList<BillingInfo>();
//        for(int i = 0 ; i < 10; i++) {
//            BillingInfo info = new BillingInfo();
//            info.setNumberShort(i>3?"12345":"666");
//
//            info.setQuantity((long)i*i);
//            billingInfoList.add(info);
//        }
        addBilling(billingInfoList);
    }

    private void loadFromDB() {
        addBilling(DatabaseConnector.getInstance().loadBillingInfo());
    }
    
    public void addBilling(List<BillingInfo> billingInfoList) {
        for(BillingInfo info : billingInfoList) {
            if(top.containsKey(info.getNumberShort())) {
                top.get(info.getNumberShort()).increaseQuantity();
            } else {
                BillingStats stats = new BillingStats();
                stats.setNumber(info.getNumberShort());
                stats.setName(DatabaseConnector.getInstance().getDestinationName(info.getNumberShort()));
                stats.setQuantity(1L);
                top.put(info.getNumberShort(), stats);
            }
        }
    }

    public void addBilling(BillingInfo info) {
        logger.info("Adding new billing information " +
                " numberFrom=" + info.getNumberFrom() +
                " numberTo=" + info.getNumber() +
                " date="+ info.getDate());
        if (top.containsKey(info.getNumberShort())) {
            top.get(info.getNumberShort()).increaseQuantity();
        } else {
            BillingStats stats = new BillingStats();
            stats.setNumber(info.getNumberShort());
            stats.setName(DatabaseConnector.getInstance().getDestinationName(info.getNumberShort()));
            stats.setQuantity(1L);
            top.put(info.getNumberShort(), stats);
        }
        if(tmpBuffer.size() <= 30) {
            tmpBuffer.add(info);
        } else {
            tmpBuffer.add(info);
            DatabaseConnector.getInstance().saveBillingInfo(tmpBuffer);
            tmpBuffer.clear();
        }
    }
    
    public List<BillingStats> getStatistics() {
        logger.info("Get statistics called from client");
        logger.info("Already have =" + top.values().size());
        List<BillingStats> statsList = new ArrayList<BillingStats>(top.values());
        Collections.sort(statsList, new Comparator<BillingStats>() {
            public int compare(BillingStats o1, BillingStats o2) {
                long thisVal = o1.getQuantity();
                long anotherVal = o2.getQuantity();
                return (thisVal<anotherVal ? 1 : (thisVal==anotherVal ? 0 : -1));
            }
        });
        if(statsList.size() == 0)
            return null;
        else if(statsList.size() <= 10) {
            return new ArrayList<BillingStats>(statsList.subList(0, statsList.size()));
        } else
            return new ArrayList<BillingStats>(statsList.subList(0, 10));
    }

    private void clearCahche() {
        top.clear();
        loadFromDB();

        List<BillingInfo> billingInfoList = new ArrayList<BillingInfo>();
//        for(int i = 0 ; i < 10; i++) {
//            BillingInfo info = new BillingInfo();
//            info.setNumberShort(i>3?"12345":"666");
//
//            info.setQuantity((long)i*i);
//            billingInfoList.add(info);
//        }
        addBilling(billingInfoList);
    }

    private void startWorker() {
        TelnetWorker worker = new TelnetWorker();
        worker.setHost("192.168.0");
    }
}
