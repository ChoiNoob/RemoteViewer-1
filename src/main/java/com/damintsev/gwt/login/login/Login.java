package com.damintsev.gwt.login.login;

import com.damintsev.gwt.common.source.utils.Dialogs;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * @author Damintsev Andrey
 *         23.04.2014.
 */
public class Login implements EntryPoint {
    @Override
    public void onModuleLoad() {

        ContentPanel panel = new ContentPanel();
        panel.setHeadingText("fuck fuck fuck fuck");

        RootPanel.get().add(panel);

        Dialogs.alert("FUCKFUCCKFUCUCUCUC");
    }
}
