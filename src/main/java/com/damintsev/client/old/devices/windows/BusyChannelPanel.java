//package com.damintsev.client.old.devices.windows;
//
//import com.damintsev.client.old.devices.Device;
//import com.damintsev.client.old.devices.graph.BusyInfo;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.i18n.client.DateTimeFormat;
//import com.google.gwt.user.client.Timer;
//import com.google.gwt.user.client.ui.IsWidget;
//import com.google.gwt.user.client.ui.Widget;
//import com.sencha.gxt.chart.client.chart.Chart;
//import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
//import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
//import com.sencha.gxt.chart.client.chart.series.LineSeries;
//import com.sencha.gxt.chart.client.chart.series.Primitives;
//import com.sencha.gxt.chart.client.draw.RGB;
//import com.sencha.gxt.chart.client.draw.sprite.Sprite;
//import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
//import com.sencha.gxt.core.client.ValueProvider;
//import com.sencha.gxt.data.shared.LabelProvider;
//import com.sencha.gxt.data.shared.ListStore;
//import com.sencha.gxt.data.shared.ModelKeyProvider;
//import com.sencha.gxt.data.shared.PropertyAccess;
//
//import java.util.Date;
//
///**
// * Created by adamintsev
// * Date: 21.08.13 10:33
// */
//public class BusyChannelPanel implements IsWidget {
//
//    public interface BillingStatsAccess extends PropertyAccess<BusyInfo> {
//        ValueProvider<BusyInfo, Date>  date();
//        ModelKeyProvider<BusyInfo> id();
//        ValueProvider<BusyInfo, Long>  busy();
//    }
//
//    private static final Long MINUTE = 1000 * 60L;
//    private static final Long HOUR = 1000 * 60 * 60L;
//    private static final Long VISIBLE_PERIOD = HOUR ;
//    private static final Long PERIOD_REQUEST = (long)(0.1 * MINUTE);
//
//    private static final BillingStatsAccess data = GWT.create(BillingStatsAccess.class);
//    private static final DateTimeFormat f = DateTimeFormat.getFormat("HH:mm");
//    private Device device;
//    private Chart<BusyInfo> chart;
//    private TimeAxis<BusyInfo> time;
//    private NumericAxis<BusyInfo> axis;
//    private Timer update;
//
//    public BusyChannelPanel(Device device) {
//        this.device = device;
//    }
//
//    public Widget asWidget() {
//        chart = new Chart<BusyInfo>(600, 400);
////        chart.setDefaultInsets(20);
//
//        ListStore<BusyInfo> store = new ListStore<BusyInfo>(data.id());
//
//        Date initial = new Date(new Date().getTime() - 1 * PERIOD_REQUEST);
//        for (int i = 0; i < 1; i++) {
//            store.add(new BusyInfo(initial, 0L, 100L));
//            initial = new Date(initial.getTime() + PERIOD_REQUEST);
//        }
////        loadStatistics(store, item.getData());
//
//        chart.setStore(store);
//
//        axis = new NumericAxis<BusyInfo>();
//        axis.setPosition(Chart.Position.LEFT);
//        axis.addField(data.busy());
//        TextSprite title = new TextSprite("Busy channel");
//        title.setFontSize(10);
//        axis.setTitleConfig(title);
//        axis.setDisplayGrid(true);
//        axis.setMinimum(0);
//        axis.setMaximum(30);
//        axis.setDisplayGrid(true);
//        chart.addAxis(axis);
//
//        time = new TimeAxis<BusyInfo>();
//        time.setField(data.date());
////        time.setStartDate(f.parse("00:00"));
////        time.setEndDate(f.parse("01:00"));
//        time.setStartDate(new Date(new Date().getTime() - VISIBLE_PERIOD));
//        time.setEndDate(new Date());
//        time.setLabelProvider(new LabelProvider<Date>() {
//            //            @Override
//            public String getLabel(Date item) {
////                return f.format(item);
//                return "";
//            }
//        });
//        TextSprite sprite = new TextSprite();
//        sprite.setRotation(315);
//        sprite.setFontSize(10);
//
//        time.setLabelConfig(sprite);
//        chart.addAxis(time);
//
//        LineSeries<BusyInfo> series = new LineSeries<BusyInfo>();
//        series.setYAxisPosition(Chart.Position.LEFT);
//        series.setYField(data.busy());
//        series.setStroke(new RGB(RGB.GREEN));
//        series.setShowMarkers(true);
//        series.setSmooth(true);
////        series.setMarkerIndex(2);
//        series.setFill(new RGB(new RGB(0, 200, 0)));
//        Sprite marker = Primitives.circle(0, 0, 1);
//        marker.setFill(new RGB(RGB.GREEN));
//        series.setMarkerConfig(marker);
//        series.setHighlighting(true);
//        chart.addSeries(series);
//
//        update = new Timer() {
//            @Override
//            public void run() {
////                loadStatistics();
//            }
//        };
////        loadOldStatistics();
//        return chart;
//    }
//
////    private void loadOldStatistics() {
////        Service.instance.loadBusyInfoStatistics(device, new AsyncCallback<List<BusyInfo>>() {
////            public void onFailure(Throwable caught) {
////                Dialogs.alert("Error while loading old busy statisitcs =" + caught.getMessage());
////            }
////
////            public void onSuccess(List<BusyInfo> result) {
////                chart.getStore().addAll(result);
////                chart.redrawChart();
////                update.run();
////                update.scheduleRepeating(PERIOD_REQUEST.intValue());
////            }
////        });
////    }
////          long i = 0;
////    private void loadStatistics() {
////        Service.instance.loadBusyInfo(device, new AsyncCallback<BusyInfo>() {
////            public void onFailure(Throwable caught) {
////                update.cancel();
////                Dialogs.alert("Error while loading busy statistics =" + caught.getMessage());
////            }
////
////            public void onSuccess(BusyInfo result) {
////                System.out.println("loaded result!!!");
//////                result = new BusyInfo(new Date(),(long) (Math.random() * 20+10), 100L);
//////                result.setId(++i);
////                if(result != null)
////                    updateChart(result);
////                else
////                    updateError();
////            }
////        });
////    }
//
//    private void updateChart(BusyInfo info) {
//        System.out.println("updateChart id=" + info.getId() + " date=" + info.getDate() + " bysy=" + info.getBusy());
//        Date endDate = info.getDate();
//        Date startDate = new Date(endDate.getTime() - VISIBLE_PERIOD);
//        if(info.getMax() == null) {
//            axis.setAdjustMaximumByMajorUnit(true);
//        } else
//            axis.setMaximum(info.getMax());
//        if(!chart.getStore().hasRecord(info))
//            chart.getStore().add(info);
//        System.out.println("start date=" + startDate);
//        System.out.println("end date=" + endDate);
//        time.setStartDate(startDate);
//        time.setEndDate(endDate);
//        chart.redrawChart();
//    }
//
//    private void updateError() {
//        System.out.println("updateError");
//        Date endDate = new Date();
//        Date startDate = new Date(endDate.getTime() - VISIBLE_PERIOD);
//        chart.getStore().add(new BusyInfo(endDate, null, 30L));
//        System.out.println("start date=" + startDate);
//        System.out.println("end date=" + endDate);
//        time.setStartDate(startDate);
//        time.setEndDate(endDate);
//        chart.redrawChart();
//    }
//
//    public void stop() {
//        update.cancel();
//    }
//}
