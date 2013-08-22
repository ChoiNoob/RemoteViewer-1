package com.damintsev.client.uiframe;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.BillingStats;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.Service;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
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
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
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

    public interface BillingStatsProperties extends PropertyAccess<BillingStats> {
//        @Editor.Path("number")
//        ModelKeyProvider<BillingStats> numberKey();

        ValueProvider<BillingStats, String> name();

        ValueProvider<BillingStats, String> number();

        ValueProvider<BillingStats, Long> quantity();
    }

    private static final BillingStatsProperties properties = GWT.create(BillingStatsProperties.class);

    private Chart<BillingStats> chart;
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
        new Draggable(panel, panel.getHeader());//.setUseProxy(true); //todo really need ?!

        ListStore<BillingStats> store = new ListStore<BillingStats>(new ModelKeyProvider<BillingStats>() {
            public String getKey(BillingStats item) {
                return item.getNumber();
            }
        });

        chart = new Chart<BillingStats>();
        chart.setStore(store);

        NumericAxis<BillingStats> axis = new NumericAxis<BillingStats>();
        axis.setPosition(Chart.Position.LEFT);
//
//        PathSprite odd = new PathSprite();
//        odd.setOpacity(1);
//        odd.setFill(new Color("#ddd"));
//        odd.setStroke(new Color("#bbb"));
//        odd.setStrokeWidth(0.5);
//        axis.setGridOddConfig(odd);
        axis.addField(properties.quantity());
        TextSprite sprite = new TextSprite("Количество звонков");
        axis.setTitleConfig(sprite);
        axis.setMinimum(0);
        axis.setDisplayGrid(true);
        chart.addAxis(axis);

        CategoryAxis<BillingStats, String> categoryAxis = new CategoryAxis<BillingStats, String>();
        categoryAxis.setPosition(Chart.Position.BOTTOM);
        categoryAxis.setField(new ValueProvider<BillingStats, String>() {
            public String getValue(BillingStats object) {
                return object.getName()==null?object.getNumber():object.getName();
            }

            public void setValue(BillingStats object, String value) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        final BarSeries<BillingStats> column = new BarSeries<BillingStats>();
        column.setYAxisPosition(Chart.Position.LEFT);
//        column.addYField(new ValueProvider<BillingStats, Number>() {
//            @Override
//            public Number getValue(BillingStats object) {
//                return object.getQuantity();
//            }
//        });
        column.addYField(properties.quantity());
        column.addColor(new RGB(148, 174, 10));
        column.setColumn(true);
//        column.setGutter(10);
        chart.addSeries(column);

        chart.addAxis(categoryAxis);
        panel.add(chart);


        for(int i = 0; i < 10; i++) {
            BillingStats info = new BillingStats();
//            info.setId((long)i);
            info.setNumber("81296770");
            info.setQuantity((long)2 * i - i);
            chart.getStore().add(info);
        }

        panel.collapse();
    }

    public ContentPanel getContent() {
        return panel;
    }

    private void scheduler() {
        Service.instance.getStatistisc(new AsyncCallback<List<BillingStats>>() {
            public void onFailure(Throwable caught) {
                Dialogs.alert("Error while getting statistics =" + caught.getMessage());
                runAnother=false;
            }

            public void onSuccess(List<BillingStats> result) {
                System.out.println("res=" + result);
                if (result != null) {
                    chart.getStore().clear();
                    chart.getStore().addAll(result);
                    if(result.size() < 10) {
                        for(int i = 0; i < 10 - result.size();i++) {
                            BillingStats stats = new BillingStats();
                            stats.setNumber("");
                            stats.setQuantity(0L);
                            chart.getStore().add(stats);
                        }
                    }
                    chart.redrawChart();
                }
            }
        });
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
        System.out.println("start Billing statistics");
        runAnother = true;
        schedule();
    }

    public void stop() {
        System.out.println("stop");
        runAnother = false;
    }
}
