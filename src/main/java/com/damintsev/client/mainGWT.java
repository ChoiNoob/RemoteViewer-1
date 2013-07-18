package com.damintsev.client;

import com.damintsev.client.frames.MenuWidget;
import com.damintsev.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

import java.awt.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class mainGWT implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
//  private static final String SERVER_ERROR = "An error occurred while "
//      + "attempting to contact the server. Please check your network "
//      + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
//  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

//  private final Messages messages = GWT.create(Messages.class);



    public void onModuleLoad() {
        Viewport viewport = new Viewport();
        BorderLayoutContainer body = new BorderLayoutContainer();

        FlowLayoutContainer header = new FlowLayoutContainer();
        header.setStyleName("header");
        body.setNorthWidget(header, new BorderLayoutContainer.BorderLayoutData(64));

        FlowLayoutContainer logo = new FlowLayoutContainer();
        logo.setStyleName("gwt_logo");
        logo.setHeight(64);
        logo.setWidth("100%");
        header.add(logo);

        FlowLayoutContainer footer = new FlowLayoutContainer();
        footer.setStyleName("footer");
        body.setSouthWidget(footer, new BorderLayoutContainer.BorderLayoutData(30));

        ContentPanel menuPanel = new ContentPanel();
        menuPanel.setHeadingText("Меню");
        menuPanel.add(MenuWidget.createMenu());

        BorderLayoutContainer.BorderLayoutData data = new BorderLayoutContainer.BorderLayoutData(.2);
        data.setCollapsible(true);
        data.setSplit(true);
        data.setFloatable(true);

        body.setWestWidget(menuPanel, data);

        ContentPanel center = new ContentPanel();
        body.setCenterWidget(center);
        viewport.add(body);
        RootPanel.get().add(viewport);
    }
}
