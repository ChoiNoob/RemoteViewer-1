package com.damintsev.server.db;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.server.db.xmldao.XMLItem;
import com.damintsev.server.db.xmldao.XMLItemList;
import com.damintsev.server.db.xmldao.XMLPosition;
import com.damintsev.server.db.xmldao.XMLPositionList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by adamintsev
 * Date: 08.08.13 16:28
 */
public class DatabaseProxy {
//    private final static String tomcatHome = System.getProperty("catalina.base");
    private final static String tomcatHome = "W:\\tmp";//System.getProperty("catalina.base");
    private final static String positionFile = tomcatHome + File.separatorChar + "filePosition.xml";
    private final static String itemsFile = tomcatHome + File.separatorChar  + "fileItems.xml";

    public List<Item> loadItemPositions() {
        List<Item> items = new ArrayList<Item>();
        try {
            File file = new File(positionFile);
            if (file.exists()) {
                Unmarshaller unmarshaller = createUnmarshaller(XMLPositionList.class);
                XMLPositionList<XMLPosition> xmlPositionList = (XMLPositionList<XMLPosition>) unmarshaller.unmarshal(file);
//                System.out.println("CPT posIs=" + xmlPositionList.getList().size());
                List<XMLItem> xmlItems = getItems();
//                System.out.println("CPT items=" + xmlItems.size());
                for(XMLPosition pos : xmlPositionList.getList()) {
                    Item item = new Item();
                    item.setCoordX(pos.getPositionX());
                    item.setCoordY(pos.getPositionY());
//                    item.setId(pos.getId());

                    XMLItem xmlItem = find(xmlItems, pos.getId());
                    item.setData(convertXML(xmlItem, xmlItems));
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    private Device convertXML(XMLItem xmlItem, List<XMLItem> itemList) {
        System.out.println("CPTT=" + xmlItem.getDeviceType());
        switch (DeviceType.valueOf(xmlItem.getDeviceType())) {
            case STATION:
                Station station = new Station();
                station.setHost(xmlItem.getHost());
                station.setPort(xmlItem.getPort());
                station.setLogin(xmlItem.getLogin());
                station.setPassword(xmlItem.getPassword());
                station.setId(xmlItem.getId());
                station.setName(xmlItem.getName());
                station.setComment(xmlItem.getComment());
                station.setStatus(Status.INIT);
                return station;
            case ISDN:
            case IP:
                CommonDevice isdn = new CommonDevice();
                isdn.setId(xmlItem.getId());
                isdn.setQuery(xmlItem.getQuery());
                isdn.setRegExp(xmlItem.getRegExp());
                isdn.setName(xmlItem.getName());
                isdn.setComment(xmlItem.getComment());
                isdn.setDeviceType(DeviceType.valueOf(xmlItem.getDeviceType()));
                isdn.setStation(findStation(xmlItem.getStationId(), itemList));
                isdn.setStatus(Status.INIT);
                return isdn;
        }
        return null;
    }

    private Station findStation(Long stationId, List<XMLItem> itemList) {
        for(XMLItem xmlItem :itemList) {
            if(xmlItem.getId().equals(stationId)){
                Station station = new Station();
                station.setHost(xmlItem.getHost());
                station.setPort(xmlItem.getPort());
                station.setLogin(xmlItem.getLogin());
                station.setPassword(xmlItem.getPassword());
                station.setId(xmlItem.getId());
                station.setName(xmlItem.getName());
                station.setComment(xmlItem.getComment());

                return station;
            }
        }
        return null;
    }

    private XMLItem find(List<XMLItem> xmlItems, Long id) {
        int index = Collections.binarySearch(xmlItems, id, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                XMLItem it = (XMLItem) o1;
                int v;
                if((v =  it.getId().compareTo((Long) o2)) != 0) return v;
                return v;
            }
        });
        return index >= 0 ? xmlItems.get(index):null;

    }

    private List<XMLItem> getItems() {
        try {
            File file = new File(itemsFile);
            if(file.exists()) {
                Unmarshaller unmarshaller = createUnmarshaller(XMLItemList.class);
                XMLItemList<XMLItem> xmlItemList = (XMLItemList<XMLItem>) unmarshaller.unmarshal(file);
                return xmlItemList.getItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public void saveItems(List<Item> items) {
        List<XMLItem>  xmlItems = new ArrayList<XMLItem>();
        List<XMLPosition> xmlPositionList = new ArrayList<XMLPosition>();
        for(Item item : items) {
            XMLPosition pos = new XMLPosition();
            pos.setId(item.getId());
            pos.setPositionX(item.getCoordX());
            pos.setPositionY(item.getCoordY());
            xmlPositionList.add(pos);

            Device data = item.getData();

            XMLItem item1 = new XMLItem();
            item1.setId(item.getId());
            item1.setName(item.getName());

            switch (data.getDeviceType()) {
                case ISDN:
                case IP:
                    CommonDevice isdn = (CommonDevice) data;
                    item1.setQuery(isdn.getQuery());
                    item1.setRegExp(isdn.getQuery());
                    item1.setName(isdn.getName());
                    item1.setComment(isdn.getComment());
                    item1.setDeviceType(isdn.getDeviceType().name());
                    item1.setStationId(isdn.getStation().getId());
                    break;
                case STATION:
                    Station station = (Station) data;
                    item1.setDeviceType(station.getDeviceType().name());
                    item1.setHost(station.getHost());
                    item1.setPort(station.getPort());
                    item1.setLogin(station.getLogin());
                    item1.setPassword(station.getPassword());
                    break;
            }
            xmlItems.add(item1);

        }
        try{
            File file = new File(positionFile);
            if(!file.exists())
                file.createNewFile();
            //save position
            Marshaller marshaller = createMarshaller(XMLPositionList.class);
            XMLPositionList<XMLPosition> list = new XMLPositionList<XMLPosition>();
            list.setList(xmlPositionList);
            marshaller.marshal(list, file);
            marshaller.marshal(list, System.out);

            System.out.println("*****************************************");

            //save objects
            file = new File(itemsFile);
            if(!file.exists())
                file.createNewFile();
            XMLItemList<XMLItem> itemList = new XMLItemList<XMLItem>();
            itemList.setItems(xmlItems);
            marshaller = createMarshaller(XMLItemList.class);
            marshaller.marshal(itemList, file);
            marshaller.marshal(itemList, System.out);

        }   catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Marshaller createMarshaller(Class clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return marshaller;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Unmarshaller createUnmarshaller(Class clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            return context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
