package com.damintsev.client.uiframe;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.service.Service;
import com.damintsev.utils.Dialogs;
import com.damintsev.client.devices.TestResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
        runTest();
    }

    private void runTest() {
        Service.instance.test((Station) device, new AsyncCallback<TestResponse>() {
            public void onFailure(Throwable caught) {
                Dialogs.alert("run test error!" + caught.toString());
            }

            public void onSuccess(TestResponse result) {
                text.setText(result.getResultText());
            }
        });
    }
}
