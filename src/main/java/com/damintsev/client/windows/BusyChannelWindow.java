package com.damintsev.client.windows;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.Service;
import com.damintsev.utils.DateUtils;
import com.damintsev.utils.Dialogs;
import com.damintsev.utils.Position;
import com.damintsev.utils.ValueProvider;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Resizable;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.RemoveEvent;

import java.util.Date;
import java.util.List;

/**
 * Created by adamintsev
 * Date: 15.08.13 15:36
 */
public class BusyChannelWindow {

    private final static int cheduleTime = 120000; //Две минуты
    private Window window;
    private ContentPanel panel;
    private Chart<BusyInfo> chart;
    private Device device;
    private boolean run;
    private TimeAxis<BusyInfo> time;
    private static final DateTimeFormat format = DateTimeFormat.getFormat("HH:mm");

    public BusyChannelWindow() {
        window = new Window();
        window.setHeadingText("Иформация по загруженности канала");
        window.setPixelSize(350,350);
        panel = new ContentPanel();
        panel.setHeaderVisible(false);
        panel.setCollapsible(false);

        ListStore<BusyInfo> store = new ListStore<BusyInfo>(new ModelKeyProvider<BusyInfo>() {
            public String getKey(BusyInfo item) {
                return item.getDate().toString();
            }
        });
        for(int i = 0; i < 2; i++) {
            BusyInfo info = new BusyInfo();
            Date date = new Date();
            long  da = date.getTime() - i * 60 * 1000 * 4;
            info.setDate(new Date(da));
            info.setBusy(1L);
            store.add(info);
        }

        chart = new Chart<BusyInfo>();
        chart.setStore(store);
        chart.setDefaultInsets(2);

        NumericAxis<BusyInfo> axis = new NumericAxis<BusyInfo>();
        axis.setPosition(Chart.Position.LEFT);
        axis.addField(new ValueProvider<BusyInfo, Number>() {
            @Override
            public Number getValue(BusyInfo object) {
                return object.getBusy();
            }
        });
        TextSprite sprite = new TextSprite("Busy channels");
        axis.setTitleConfig(sprite);
//        axis.setWidth(50);
//        axis.setMaximum(100);
        axis.setMinimum(0);
        axis.setDisplayGrid(true);
        chart.addAxis(axis);
        LineSeries<BusyInfo> series = new LineSeries<BusyInfo>();
        series.setYAxisPosition(Chart.Position.LEFT);
        series.setYField(new ValueProvider<BusyInfo, Number>() {
            @Override
            public Number getValue(BusyInfo object) {
                return object.getBusy();
            }
        });
        series.setStroke(new RGB(148, 174, 10));
        series.setShowMarkers(true);
        series.setMarkerIndex(1);
        Sprite marker = Primitives.circle(0, 0, 6);
        marker.setFill(new RGB(148, 174, 10));
        series.setMarkerConfig(marker);
        chart.addSeries(series);
        chart.setShadowChart(true);

        PathSprite odd = new PathSprite();
        odd.setOpacity(1);
        odd.setFill(new Color("#ddd"));
        odd.setStroke(new Color("#bbb"));
        odd.setStrokeWidth(0.5);
        axis.setGridOddConfig(odd);

        time = new TimeAxis<BusyInfo>();
        time.setField(new ValueProvider<BusyInfo, Date>() {
            @Override
            public Date getValue(BusyInfo object) {
                if (object.getDate() != null) {
                    return object.getDate();
                } else
                    return new Date();
            }
        });
        Date date = new Date();
        Date startDate = new Date(date.getTime() - 1000 * 60 * 5);
        Date endDate = new Date(date.getTime() + 1000 * 60 * 5);
        time.setStartDate(startDate);
        time.setEndDate(endDate);
        time.setLabelProvider(new LabelProvider<Date>() {
//            @Override
            public String getLabel(Date item) {
                    return format.format(item);
            }
        });
        chart.addAxis(time);

        panel.add(chart);
        window.add(panel);

        window.addHideHandler(new HideEvent.HideHandler() {
            public void onHide(HideEvent event) {
                run = false;
            }
        });

        window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent event) {
                chart.setPixelSize(event.getWidth() - 5, event.getHeight() - 5);
            }
        });
    }

    public ContentPanel show(UIItem item) {
        this.device = item.getData();
        Position pos = item.getPosition();
        int width = item.getWidth();
        window.setPosition(pos.x + width + 10, pos.y);
        window.show();
        BusyInfo b = new BusyInfo();
        b.setId((long) 1);
        chart.getStore().add(b);
        load();
        run = true;
        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            public boolean execute() {
                System.out.println("execute");
                load();
                return run;
            }
        }, cheduleTime);
        
        return panel;
    }

    public void load() {
        chart.mask();
        Service.instance.loadBusyInfo(device, new AsyncCallback<BusyInfo>() {
            public void onFailure(Throwable caught) {
                run = false;
                Dialogs.alert("Error loading busy info " + caught.getMessage());
            }

            public void onSuccess(BusyInfo result) {
                chart.getStore().clear();
                NumericAxis<BusyInfo> axis = (NumericAxis<BusyInfo>) chart.getAxis(Chart.Position.LEFT);
                chart.unmask();
                if (result != null) {
                    axis.setMaximum(result.getMax());

                    Date dateS = time.getStartDate();
                    Date startDate = new Date(dateS.getTime() + cheduleTime);
                    System.out.println("start=" + startDate);
                    System.out.println("end=" + result.getDate());
                    chart.getStore().add(result);

                    time.setStartDate(startDate);
                    time.setEndDate(result.getDate());
                    chart.redrawChart();
                }
            }
        });
    }
}
