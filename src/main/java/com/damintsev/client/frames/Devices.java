package com.damintsev.client.frames;

import com.damintsev.client.dao.Device;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.dom.client.Document;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 30.07.13
 * Time: 22:34
 */
public class Devices {

    private static Devices instance;

    public static Devices getInstance() {
        if(instance == null) instance = new Devices();
        return instance;
    }

    private AbsolutePanel panel;

    private Devices() {
        panel = new AbsolutePanel();
//        panel.setHeadingText("Устройства");

        ListStore<Device> store = new ListStore<Device>(new ModelKeyProvider<Device>() {
            public String getKey(Device item) {
                return item.getId();
            }
        });

        List<ColumnConfig<Device,?>> list = new ArrayList<ColumnConfig<Device, ?>>();

        ColumnConfig<Device, ImageResource> i1 = new ColumnConfig<Device, ImageResource>(new ValueProvider<Device, ImageResource>() {
            public ImageResource getValue(Device object) {
                if(object.getState().equals("0"))
                    return IconHelper.getImageResource(UriUtils.fromString("/web/img/working.png"), 74, 74);
                else
                    return IconHelper.getImageResource(UriUtils.fromString("/web/img/not_working.png"), 64, 64);
            }

            public void setValue(Device object, ImageResource value) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        i1.setCell(new ImageResourceCell());
        list.add(i1);

        list.add(new ColumnConfig<Device, String>(new ValueProvider<Device, String>() {
            public String getValue(Device object) {
                return object.getName();
            }

            public void setValue(Device object, String value) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));
        list.add(new ColumnConfig<Device, String>(new ValueProvider<Device, String>() {
            public String getValue(Device object) {
                return object.getAddress();
            }

            public void setValue(Device object, String value) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));

        Element e =DOM.createDiv();
        e.setClassName("");
        e.setDraggable("true");
        e.setInnerText("lalala");
//        Document.get().appendChild(e);
        Grid<Device> grid = new Grid<Device>(store, new ColumnModel<Device>(list));

        HTML html = new HTML("<div class=\"test\">lslsl</div>");
        html.getElement().setInnerText("FUCKFUCK");
        html.getElement().setDraggable("true");
        html.getElement().setPropertyString("width", "150px");// height: 150px; padding: 0.5em;);
        html.setPixelSize(150,150);// height: 150px; padding: 0.5em;);
        html.getElement().setPropertyString("background-color", "#E6E6FA");
        html.getElement().getStyle().setBackgroundColor("#E6E6FA");
//        html.set
//        html.getElement().
//        Element el = DOM.createDiv();
//
//        el.setPropertyString("background-color", "#E6E6FA");
//        el.getStyle().s
        panel.add(html);



//        panel.add(grid);

        List<Device> devices = new ArrayList<Device>();
        for(int  i = 0; i < 7; i++) {
            Device device = new Device();
            device.setId("" + i);
            device.setAddress("Станция " + i);
            device.setName("Имя " + i);
            device.setState(i > 2 ? "0" : "1");
            devices.add(device);
        }
        grid.getStore().addAll(devices);
    }

    public AbsolutePanel getContent() {
        return panel;
    }
}
