package com.damintsev.client.uiframe;

import com.damintsev.client.devices.Device;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * User: Damintsev Andrey
 * Date: 12.08.13
 * Time: 23:49
 */
public class TelnetWindow extends Window {

    private static TelnetWindow instance;

    public static TelnetWindow getInstance() {
        if(instance==null) instance = new TelnetWindow() ;
        return instance;
    }

    private TextArea text;
    private Device device;

    private TelnetWindow() {
        setPixelSize(350,350);
        setClosable(true);
        ContentPanel panel = new ContentPanel();
        text = new TextArea();
        panel.add(text);
        setWidget(panel);
    }

    public void show(Device device) {
        this.device = device;
        super.show();
//        runTest();
    }
}
