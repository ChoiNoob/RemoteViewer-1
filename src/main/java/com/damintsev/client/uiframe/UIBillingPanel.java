package com.damintsev.client.uiframe;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.service.Service;
import com.damintsev.utils.ListLoadResultImpl;
import com.damintsev.utils.ValueProvider;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
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

    private Grid<BillingInfo> grid;
    private ContentPanel panel;
    private boolean runAnother = false;

    private UIBillingPanel() {
        panel = new ContentPanel();
        panel.setHeadingText("Биллинговая информация");
        panel.setPixelSize(255, 250);
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
//        new Draggable(panel); //todo really need ?!

        List<ColumnConfig<BillingInfo, ?>> columns = new ArrayList<ColumnConfig<BillingInfo, ?>>();
        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
            @Override
            public String getValue(BillingInfo object) {
                return object.getStreamName();
            }
        },100, "Поток"));
        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
            @Override
            public String getValue(BillingInfo object) {
                return object.getValue();
            }
        },75, "Значение"));
        columns.add(new ColumnConfig<BillingInfo, String>(new ValueProvider<BillingInfo, String>() {
            @Override
            public String getValue(BillingInfo object) {
                return object.getValue2();
            }
        },75, "Значение 2"));

        grid = new Grid<BillingInfo>(new ListStore<BillingInfo>(new ModelKeyProvider<BillingInfo>() {
            public String getKey(BillingInfo item) {
                return item.getId().toString();
            }
        }), new ColumnModel<BillingInfo>(columns));


        RpcProxy<ListLoadConfig, ListLoadResultImpl<BillingInfo>> proxy = new RpcProxy<ListLoadConfig, ListLoadResultImpl<BillingInfo>>() {
            @Override
            public void load(ListLoadConfig loadConfig, AsyncCallback<ListLoadResultImpl<BillingInfo>> callback) {
                System.out.println("loadaaaaa");
                Service.instance.getBillingInfo(callback);
            }
        };
        ListLoader<ListLoadConfig, ListLoadResultImpl<BillingInfo>> loader = new ListLoader<ListLoadConfig, ListLoadResultImpl<BillingInfo>>(proxy);
        loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig,BillingInfo,ListLoadResultImpl<BillingInfo>>(grid.getStore()));
        grid.setLoader(loader);
//        start();
        panel.add(grid);
        panel.collapse();
    }

    public ContentPanel getContent() {
        return panel;
    }

    private void scheduler() {
        grid.getLoader().load();
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
