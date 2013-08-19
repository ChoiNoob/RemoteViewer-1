package com.damintsev.client.uiframe;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.Service;
import com.damintsev.utils.ListLoadResultImpl;
import com.damintsev.utils.ValueProvider;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.fx.client.Draggable;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.ExpandEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamintsev
 * Date: 14.08.13 15:04
 */
public class UIBillingPanel {

    private static UIBillingPanel instance;

    public static UIBillingPanel getInstance() {
        if(instance == null) instance = new UIBillingPanel();
        return instance;
    }

    private Chart<BillingInfo> chart;
    private ContentPanel panel;
    private boolean runAnother = false;

    private UIBillingPanel() {
        panel = new ContentPanel();
        panel.setHeadingText("Информация по звонкам");
        panel.setPixelSize(900, 500);
        panel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        panel.getElement().getStyle().setLeft(10, Style.Unit.PX);
        panel.getElement().getStyle().setBottom(10, Style.Unit.PX);
        panel.setCollapsible(true);

        panel.addExpandHandler(new ExpandEvent.ExpandHandler() {
            public void onExpand(ExpandEvent event) {
                start();
            }
        });
        panel.addCollapseHandler(new CollapseEvent.CollapseHandler() {
            public void onCollapse(CollapseEvent event) {
                stop();
            }
        });
        new Draggable(panel); //todo really need ?!

        ListStore<BillingInfo> store = new ListStore<BillingInfo>(new ModelKeyProvider<BillingInfo>() {
            public String getKey(BillingInfo item) {
                return item.getId().toString();
            }
        });

        chart = new Chart<BillingInfo>();
        chart.setStore(store);

        NumericAxis<BillingInfo> axis = new NumericAxis<BillingInfo>();
        axis.setPosition(Chart.Position.LEFT);
//
        PathSprite odd = new PathSprite();
        odd.setOpacity(1);
        odd.setFill(new Color("#ddd"));
        odd.setStroke(new Color("#bbb"));
        odd.setStrokeWidth(0.5);
        axis.setGridOddConfig(odd);

//        final LineSeries<BillingInfo> series3 = new LineSeries<BillingInfo>();
//        series3.setYAxisPosition(Chart.Position.LEFT);
//        series3.setYField(new ValueProvider<BillingInfo, Number>() {
//            @Override
//            public Number getValue(BillingInfo object) {
//                return object.getQuantity();
//            }
//        });
//        series3.setStroke(new RGB(32, 68, 186));
//        series3.setShowMarkers(true);
//        series3.setSmooth(true);
//        series3.setFill(new RGB(32, 68, 186));
//        Sprite marker = Primitives.diamond(0, 0, 6);
//        marker.setFill(new RGB(32, 68, 186));
//        series3.setMarkerConfig(marker);
//        series3.setHighlighting(true);
//        chart.addSeries(series3);
//        chart.setShadowChart(true);
        axis.addField(new ValueProvider<BillingInfo, Number>() {
            @Override
            public Number getValue(BillingInfo object) {
                return object.getQuantity();
            }
        });
        TextSprite sprite = new TextSprite("Количество звонков");
        axis.setTitleConfig(sprite);
//        axis.setWidth(50);
//        axis.setMaximum(100);
        axis.setMinimum(0);
        axis.setDisplayGrid(true);
        chart.addAxis(axis);

        CategoryAxis<BillingInfo, String> categoryAxis = new CategoryAxis<BillingInfo, String>();
        categoryAxis.setPosition(Chart.Position.BOTTOM);
        categoryAxis.setField(new ValueProvider<BillingInfo, String>() {
            @Override
            public String getValue(BillingInfo object) {
                if (object.getNumberFrom() == null) {
                    return object.getNumber();
                } else return object.getNumberFrom();
            }
        });

        final BarSeries<BillingInfo> column = new BarSeries<BillingInfo>();
        column.setYAxisPosition(Chart.Position.LEFT);
        column.addYField(new ValueProvider<BillingInfo, Number>() {
            @Override
            public Number getValue(BillingInfo object) {
                return object.getQuantity();
            }
        });
        column.addColor(new RGB(148,174,10));
        column.setColumn(true);
        chart.addSeries(column);

        chart.addAxis(categoryAxis);

        panel.add(chart);


        for(int i = 0; i < 10; i++) {
            BillingInfo info = new BillingInfo();
            info.setId((long)i);
            info.setNumber("81296770");
            info.setQuantity((long)2 * i - i);
            chart.getStore().add(info);
        }

//        List<ColumnConfig<BillingInfo, ?>> columns = new ArrayList<ColumnConfig<BillingInfo, ?>>();
//        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
//            @Override
//            public String getValue(BillingInfo object) {
//                return object.getStreamName();
//            }
//        },100, "Поток"));
//        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
//            @Override
//            public String getValue(BillingInfo object) {
//                return object.getValue();
//            }
//        },75, "Значение"));
//        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
//            @Override
//            public String getValue(BillingInfo object) {
//                return object.getValue2();
//            }
//        },75, "Значение 2"));
//
//        grid = new Grid<BillingInfo>(new ListStore<BillingInfo>(new ModelKeyProvider<BillingInfo>() {
//            public String getKey(BillingInfo item) {
//                return item.getId().toString();
//            }
//        }), new ColumnModel<BillingInfo>(columns));
//
//
//        RpcProxy<ListLoadConfig, ListLoadResultImpl<BillingInfo>> proxy = new RpcProxy<ListLoadConfig, ListLoadResultImpl<BillingInfo>>() {
//            @Override
//            public void load(ListLoadConfig loadConfig, AsyncCallback<ListLoadResultImpl<BillingInfo>> callback) {
//                System.out.println("loadaaaaa");
//                Service.instance.getBillingInfo(callback);
//            }
//        };
//        ListLoader<ListLoadConfig, ListLoadResultImpl<BillingInfo>> loader = new ListLoader<ListLoadConfig, ListLoadResultImpl<BillingInfo>>(proxy);
//        loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig,BillingInfo,ListLoadResultImpl<BillingInfo>>(grid.getStore()));
//        grid.setLoader(loader);
////        start();
//        panel.add(grid);
//        panel.collapse();
    }

    public ContentPanel getContent() {
        return panel;
    }

    private void scheduler() {
//        grid.getLoader().load();
    }

    public void schedule() {
        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            public boolean execute() {
                scheduler();
                return runAnother;
            }
        }, 10000);
    }

    public void start() {
        System.out.println("start");
        runAnother = true;
        schedule();
    }

    public void stop() {
        System.out.println("stop");
        runAnother = false;
    }
}
