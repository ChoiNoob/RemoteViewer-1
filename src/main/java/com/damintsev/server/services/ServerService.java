package com.damintsev.server.services;

import com.damintsev.client.devices.*;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.ftp.FTPService;
import com.damintsev.server.telnet.SchedulerNew;
import com.damintsev.utils.ListLoadResultImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServerService.class);

    public Boolean saveItems(List<Item> items) {
        logger.info("Call saveItems()");
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveItems(items);
        return true;
    }

    public List<Item> loadItems() {
        logger.info("Call loadItems()");
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadItemPositions();
    }

    public Device getState() {
        return null;
    }

    public TestResponse test(Station device) {
        return SchedulerNew.getInstance().test(device); //todo
    }

    public Device checkDevice(Device device) {
        logger.info("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
        Device result = null;
        try {
            result = SchedulerNew.getInstance().checkDevice(device);
            return result;
        }catch (Exception e) {
            logger.error("Error while processing checkDevice: ", e);
            throw new RuntimeException(e);
        }
    }

    public ListLoadResultImpl<BillingInfo> getBillingInfo() {
        ListLoadResultImpl<BillingInfo> list = new ListLoadResultImpl<BillingInfo>();
        list.setData(FTPService.getInstance().getBills());
        return list;
    }

    public void saveFTPSettings(FTPSettings settings) {
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveFTP(settings);
    }

    public FTPSettings loadFTPSettings() {
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadFTP();
    }
}
