package com.damintsev.client.windows;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.Service;
import com.damintsev.utils.Dialogs;
import com.damintsev.utils.ValueProvider;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.widget.core.client.Window;

import java.util.List;

/**
 * Created by adamintsev
 * Date: 15.08.13 15:36
 */
public class BusyChannelWindow {

    private Window window;
    private ContentPanel panel;
    private Chart<BusyInfo> chart;

    public BusyChannelWindow() {
        window = new Window();
        window.setHeadingText("Иформация по загруженности канала");
        window.setPixelSize(350,350);
        window.setPosition(20,20);
        panel = new ContentPanel();
        panel.setHeaderVisible(false);

        ListStore<BusyInfo> store = new ListStore<BusyInfo>(new ModelKeyProvider<BusyInfo>() {
            public String getKey(BusyInfo item) {
                return item.getId().toString();
            }
        });

        chart = new Chart<BusyInfo>();
        chart.setStore(store);

        NumericAxis<BusyInfo> axis = new NumericAxis<BusyInfo>();
        axis.setPosition(Chart.Position.LEFT);
//
        PathSprite odd = new PathSprite();
        odd.setOpacity(1);
        odd.setFill(new Color("#ddd"));
        odd.setStroke(new Color("#bbb"));
        odd.setStrokeWidth(0.5);
        axis.setGridOddConfig(odd);

        final LineSeries<BusyInfo> series3 = new LineSeries<BusyInfo>();
        series3.setYAxisPosition(Chart.Position.LEFT);
        series3.setYField(new ValueProvider<BusyInfo, Number>() {
            @Override
            public Number getValue(BusyInfo object) {
                return object.getBusy();
            }
        });
        series3.setStroke(new RGB(32, 68, 186));
        series3.setShowMarkers(true);
        series3.setSmooth(true);
        series3.setFill(new RGB(32, 68, 186));
        Sprite marker = Primitives.diamond(0, 0, 6);
        marker.setFill(new RGB(32, 68, 186));
        series3.setMarkerConfig(marker);
        series3.setHighlighting(true);
        chart.addSeries(series3);
        chart.setShadowChart(true);
        axis.addField(new ValueProvider<BusyInfo, Number>() {
            @Override
            public Number getValue(BusyInfo object) {
                return object.getBusy();
            }
        });
        TextSprite sprite = new TextSprite("Busy channels");
        axis.setTitleConfig(sprite);
//        axis.setWidth(50);
        axis.setMaximum(100);
        axis.setMinimum(0);
        axis.setDisplayGrid(true);
        chart.addAxis(axis);

        CategoryAxis<BusyInfo, String> categoryAxis = new CategoryAxis<BusyInfo, String>();
        categoryAxis.setPosition(Chart.Position.BOTTOM);
        categoryAxis.setField(new ValueProvider<BusyInfo, String>() {
            @Override
            public String getValue(BusyInfo object) {
                if (object.getDate() != null) {
                    DateTimeFormat format = DateTimeFormat.getFormat("HH:mm");
                    return format.format(object.getDate());
                } else return "";
            }
        });

        chart.addAxis(categoryAxis);

        panel.add(chart);
        window.add(panel);

//        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
//            public boolean execute() {
//                System.out.println("execute");
//                addDevice();
//                return true;
//            }
//        }, 10000);
    }

    public ContentPanel show(Device device) {
        window.show();
        BusyInfo b = new BusyInfo();
        b.setId((long) i);
        chart.getStore().add(b);
        chart.mask();
        Service.instance.loadBusyInfo(device, new AsyncCallback<List<BusyInfo>>() {
            public void onFailure(Throwable caught) {
                Dialogs.alert("Error loading busy info " + caught.getMessage());
            }

            public void onSuccess(List<BusyInfo> result) {
                chart.getStore().clear();
                NumericAxis<BusyInfo> axis = (NumericAxis<BusyInfo>) chart.getAxis(Chart.Position.LEFT);

                chart.unmask();
                if(result != null)  {
                    for(BusyInfo info : result) {
                        if(axis.getMaximum() != info.getMax()){
                            axis.setMaximum(info.getMax());
                        }
                        chart.getStore().add(info);
                    }
                }
            }
        });
        return panel;
    }
     int i=1;
    public void addDevice() {
        System.out.println("add i=" + i);
        BusyInfo b = new BusyInfo();
        b.setId((long) i);
        b.setBusy((long) i + i);
//        b.se((long) i + i + i);
//        b.setType("Type");
//        chart.getStore().clear();
        chart.getStore().add(b);
        chart.redrawChart();
        i++;
    }
}
