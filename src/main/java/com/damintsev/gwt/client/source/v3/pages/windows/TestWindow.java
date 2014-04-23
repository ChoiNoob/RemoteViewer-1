package com.damintsev.gwt.client.source.v3.pages.windows;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * User: adamintsev Date: 25.10.13 Time: 15:15
 */
public class TestWindow {

    public void show() {
        window.show();
    }

    interface MyBinder extends UiBinder<Widget, TestWindow> {
    }

    private static MyBinder uiBinder = GWT.create(MyBinder.class);

    @UiField
    Window window;
    @UiField
    TextField name;
    @UiField
    TextField comment;

    TestWindow(){
        uiBinder.createAndBindUi(this);
//        window.setData("op");
    }

    @UiHandler("closeButton")
    public void onCloseButtonClicked(SelectEvent event) {
        window.hide();
    }

}
