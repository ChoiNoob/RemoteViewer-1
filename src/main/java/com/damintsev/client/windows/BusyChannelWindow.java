package com.damintsev.client.windows;

import com.damintsev.client.devices.UIItem;
import com.damintsev.utils.Position;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.HideEvent;

/**
 * Created by adamintsev
 * Date: 15.08.13 15:36
 */
public class BusyChannelWindow {

    private Window window;
    private BusyChannelPanel busyChannelPanel;

    public BusyChannelWindow() {
        window = new Window();
        window.setHeadingText("Иформация по загруженности канала");
        window.setPixelSize(530,230);

        window.addHideHandler(new HideEvent.HideHandler() {
            public void onHide(HideEvent event) {
                if(busyChannelPanel != null)
                    busyChannelPanel.stop();
            }
        });
    }

    public void show(UIItem item) {
//        busyChannelPanel = new BusyChannelPanel(item.getData());
//        Position pos = item.getPosition();
//        int width = item.getWidth();
//        window.setPosition(pos.x + width + 10, pos.y);
//        window.add(busyChannelPanel);
        window.show();
    }
}
