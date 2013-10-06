package com.damintsev.server;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.BillingStats;
import com.damintsev.server.db.DatabaseConnector;
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
    private TreeMap<String, String> prefixMap;
    
    private BillingStatistics() {
        top = new TreeMap<String, BillingStats>();
        tmpBuffer = new ArrayList<BillingInfo>(30);
        prefixMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.length() == o2.length() ? 0 : o1.length() > o2.length() ? 1 : -1;
            }
        });
        loadFromDB();
        loadPrefixMap();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clearCache();
            }
        }, 1000 * 60 * 15, 1000 * 60 * 15);
    }

    private void loadPrefixMap() {
        prefixMap = DatabaseConnector.getInstance().loadPrefixMap();
    }

    private void loadFromDB() {
        addBilling(DatabaseConnector.getInstance().loadBillingInfo());
    }
    
    public void addBilling(List<BillingInfo> billingInfoList) {
        for(BillingInfo info : billingInfoList) {
           addBilling(info);
        }
    }

    public void addBilling(BillingInfo info) {
        logger.info("Adding new billing information " +
                " numberFrom=" + info.getNumberFrom() +
                " numberTo=" + info.getNumber() +
                " date="+ info.getDate());
        findTrimNumber(info);
        if (top.containsKey(info.getNumberShort())) {
            top.get(info.getNumberShort()).increaseQuantity();
        } else {
            BillingStats stats = new BillingStats();
            stats.setNumber(info.getNumberShort());
            stats.setName(info.getName());
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

    private void findTrimNumber(BillingInfo info) {
        for(String prefix : prefixMap.keySet()) {
            if (info.getNumberShort().startsWith(prefix)) {
                String tmp = info.getNumberShort();
                System.out.println("CPT=" + tmp + " short=" + tmp.substring(0, tmp.length() - 1));
                info.setNumberShort(tmp.substring(0, prefix.length()));
                info.setName(prefixMap.get(prefix));
            }
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

    private void clearCache() {
        DatabaseConnector.getInstance().saveBillingInfo(tmpBuffer);
        tmpBuffer.clear();
        top.clear();
        loadFromDB();
        prefixMap.clear();
        loadPrefixMap();
    }

    private void startWorker() {
        TelnetWorker worker = new TelnetWorker();
        worker.setHost("192.168.0");
    }
}
