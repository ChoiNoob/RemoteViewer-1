package com.damintsev.client.uiframe;

import com.damintsev.client.devices.BusiChannel;
import com.damintsev.utils.ValueProvider;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.chart.client.chart.Chart;

/**
 * Created by adamintsev
 * Date: 15.08.13 15:36
 */
public class BusiChannelPanel {

    private ContentPanel panel;
        private Chart<BusiChannel> chart;
    public BusiChannelPanel() {
        panel = new ContentPanel();
        panel.setPixelSize(500,500);

        ListStore<BusiChannel> store = new ListStore<BusiChannel>(new ModelKeyProvider<BusiChannel>() {
            public String getKey(BusiChannel item) {
                return item.getId().toString();
            }
        });


        chart = new Chart<BusiChannel>();
        chart.setStore(store);

        NumericAxis<BusiChannel> axis = new NumericAxis<BusiChannel>();
        axis.setPosition(Chart.Position.LEFT);
//        axis.addField(new ValueProvider<BusiChannel, Number>() {
//            @Override
//            public Number getValue(BusiChannel object) {
//                System.out.println("NumericAxis getFree=" + object.getFree());
//                return object.getFree();
//            }
//        });
        axis.addField(new ValueProvider<BusiChannel, Number>() {
            @Override
            public Number getValue(BusiChannel object) {
                System.out.println("NumericAxis getBusu=" + object.getFree());
                return object.getBusu();
            }
        });
        TextSprite sprite = new TextSprite("YYY");
        axis.setTitleConfig(sprite);
//        axis.setWidth(50);
        axis.setMaximum(100);
        axis.setMinimum(0);

        chart.addAxis(axis);

        CategoryAxis<BusiChannel, String> categoryAxis = new CategoryAxis<BusiChannel, String>();
        categoryAxis.setPosition(Chart.Position.BOTTOM);
        categoryAxis.setField(new ValueProvider<BusiChannel, String>() {
            @Override
            public String getValue(BusiChannel object) {
                return object.getType();
            }
        });
        chart.addAxis(categoryAxis);

        BarSeries<BusiChannel> column = new BarSeries<BusiChannel>();
        column.addYField(new ValueProvider<BusiChannel, Number>() {
            @Override
            public Number getValue(BusiChannel object) {
                System.out.println("addYField getFree=" + object.getFree());
                return object.getBusu();
            }
        });
        column.addYField(new ValueProvider<BusiChannel, Number>() {
            @Override
            public Number getValue(BusiChannel object) {
                System.out.println("addYField getBusu=" + object.getFree());
                return object.getFree() ;
            }
        });
        column.setColumn(true);
        column.setYAxisPosition(Chart.Position.LEFT);
        chart.addSeries(column);

         chart.redrawChart();

        
        
        panel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        panel.getElement().getStyle().setTop(50, Style.Unit.PX);
        panel.getElement().getStyle().setLeft(50, Style.Unit.PX);
        panel.add(chart);

        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {
            public boolean execute() {
                System.out.println("execute");
                addDevice();
                return true;
            }
        }, 10000);
    }

    public ContentPanel getPanel() {
        return panel;
    }
     int i=1;
    public void addDevice() {
        System.out.println("add i=" + i);
        BusiChannel b = new BusiChannel();
        b.setId((long) i);
        b.setBusu((long) i + i);
        b.setFree((long) i + i + i);
        b.setType("Type");
        chart.getStore().clear();
        chart.getStore().add(b);
        chart.redrawChart();
        i++;
    }
}
