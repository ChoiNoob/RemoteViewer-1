package com.damintsev.client.v3.uiitems;

import com.damintsev.common.uientity.Task;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: adamintsev
 * Date: 22.11.13
 */
public class UITest extends Composite {

    interface Binder extends UiBinder<Widget, UITest> {
    }

    private static Binder binder = GWT.create(Binder.class);

    private Task task;

    public UITest(Task task) {
        initWidget(binder.createAndBindUi(this));
        this.task = task;
        image.setUrl("image?type=station");
        label.setText("FUCKCKCKCKCKCKC");
    }

    @UiField
    Label label;

    @UiField
    Image image;
}
