//package com.damintsev.server.services;
//
//import com.damintsev.client.devices.*;
//import com.damintsev.client.devices.enums.DeviceType;
//import com.damintsev.client.devices.graph.BusyInfo;
//import com.damintsev.client.service.ClientService;
//import com.damintsev.client.v3.items.Station;
//import com.damintsev.server.old.BillingStatistics;
//import com.damintsev.server.old.CleanManager;
////import com.damintsev.server.db.DatabaseConnector;
////import com.damintsev.server.db.DatabaseConnector;
//import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.sencha.gxt.data.shared.loader.PagingLoadResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.*;
//
///**
// * User: Damintsev Andrey
// * Date: 08.08.13
// * Time: 0:22
// */
//public class ServerService extends RemoteServiceServlet implements ClientService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ServerService.class);
//
//    public ServerService() {
//        Timer timer = new Timer();
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.add(Calendar.DAY_OF_MONTH, 1);
//        timer.scheduleAtFixedRate(
//                new TimerTask() {
//                    @Override
//                    public void run() {
//                        CleanManager.getInstance().cleanBusyInfo();
//                    }
//                },
//                cal.getTime(),
//                1000 * 60 * 60 * 24 * 7
//        );
////        BillingWorker.getInstance();
//
//       // TestStatistics s = new TestStatistics();    s.start();
//    }
//
//    public Boolean saveItems(List<Item> items) {
//        logger.info("Call saveItems()");
////        DatabaseConnector.getInstance().saveUIPosition(items);
//        return true;
//    }
//
//    public List<Item> loadItems() {
//        return null;//;DatabaseConnector.getInstance().loadItems();
//    }
//
//    public void start() {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public void stop() {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//
//    public FTPSettings saveFTPSettings(FTPSettings settings) {
////        DatabaseProxy proxy = new DatabaseProxy();
////        proxy.saveFTP(settings);
//       return null;//DatabaseConnector.getInstance().saveFTPSettings(settings);
//    }
//
//    public FTPSettings loadFTPSettings(Station station) {
////        DatabaseProxy proxy = new DatabaseProxy();
////        return proxy.loadFTP(station);
//        return null;//DatabaseConnector.getInstance().loadFTPSettings(station);
//    }
//
//    public Device saveDevice(Device device) {
//        logger.info("calling saveDevice()");
////        Device dev = DatabaseConnector.getInstance().saveDevice(device);
////        TelnetScheduler.getInstance().updateDevice(dev);
////        BillingWorker.getInstance().updateStation(dev);
////        return dev;
//        return null;
//    }
//
//    public void deleteDevice(Device device) {
////        TelnetScheduler.getInstance().deleteDevice(device);
////        DatabaseConnector.getInstance().deleteDevice(device);
////        BillingWorker.getInstance().deleteStation(device);
//    }
//
//    public BusyInfo loadBusyInfo(Device device) {
//        return null;//DatabaseConnector.getInstance().getBusyInfo(device);
//    }
//
//    public Device loadDevice(Long deviceId, DeviceType deviceType) {
//        return null;//DatabaseConnector.getInstance().loadDevice(deviceId, deviceType);
//    }
//
//    public List<BillingStats> getStatistisc() {
//        return BillingStatistics.getInstance().getStatistics();
//    }
//
//    public PagingLoadResult<Station> getStationList() throws Exception {
////        List<Station> stations = DatabaseConnector.getInstance().getStationList();
//        return null;//new PagingLoadResultBean<Station>(stations, 0, stations.size());
//    }
//
//    public List<BusyInfo> loadBusyInfoStatistics(Device device) {
//        return null;//DatabaseConnector.getInstance().getBusyInfoStatistics(device);
//    }
//
//    public List<Device> getItemsState() {
//        return null;//TelnetScheduler.getInstance().getDeviceState();
//    }
//
//    public void hardReset() {
////        TelnetScheduler.getInstance().hardReset();
//    }
//
//    public TreeMap<String, String> loadPrefix() {
//        return null;//DatabaseConnector.getInstance().loadPrefixMap();
//    }
//
//    public void savePrefix(TreeMap<String, String> prefixMap) {
//        logger.info("savePrefix");
////        DatabaseConnector.getInstance().savePrefixMap(prefixMap);
//    }
//}
