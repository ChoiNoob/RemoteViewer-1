package com.damintsev.client;

import com.damintsev.client.uiframe.UIItem;
import com.damintsev.client.uiframe.UIRootPanel;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;

public class mainGWT implements EntryPoint {

//    public void onModuleLoad() {
//        Viewport viewport = new Viewport();
////        viewport.setStyleName("gwt_main");
//        BorderLayoutContainer body = new BorderLayoutContainer();
//        body.setStyleName("gwt_main");
////        FlowLayoutContainer header = new FlowLayoutContainer();
////        header.setStyleName("header");
////        body.setNorthWidget(header, new BorderLayoutContainer.BorderLayoutData(64));
//
//        FlowLayoutContainer logo = new FlowLayoutContainer();
//        logo.setStyleName("gwt_logo");
//        logo.setHeight(64);
//        logo.setWidth("100%");
////        header.add(logo);
//
//        FlowLayoutContainer footer = new FlowLayoutContainer();
//        footer.setStyleName("footer");
////        footer.add(logo);
//        body.setSouthWidget(footer, new BorderLayoutContainer.BorderLayoutData(30));
//
//        ContentPanel menuPanel = new ContentPanel();
//        menuPanel.setHeadingText("Меню");
//        menuPanel.add(MenuWidget.createMenu());
//
//        BorderLayoutContainer.BorderLayoutData data = new BorderLayoutContainer.BorderLayoutData(.2);
//        data.setCollapsible(true);
//        data.setSplit(true);
//        data.setFloatable(true);
//
//        body.setWestWidget(menuPanel, data);
//
//        AbsolutePanel center = Devices.getInstance().getContent();
//        body.setCenterWidget(center);
//        viewport.add(body);
//        RootPanel.get().add(viewport);
//    }
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable throwable) {
                String text = "Uncaught exception: ";
                while (throwable != null) {
                    StackTraceElement[] stackTraceElements = throwable.getStackTrace();
                    text += throwable.toString() + "\n";
                    for (int i = 0; i < stackTraceElements.length; i++) {
                        text += "    at " + stackTraceElements[i] + "\n";
                    }
                    throwable = throwable.getCause();
                    if (throwable != null) {
                        text += "Caused by: ";
                    }
                }
                DialogBox dialogBox = new DialogBox(true, false);
                DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
                System.err.print(text);
                text = text.replaceAll(" ", "&nbsp;");
                dialogBox.setHTML("<pre>" + text + "</pre>");
                dialogBox.center();
            }
        });

        Scheduler.get().scheduleDeferred(new Command() {
            public void execute() {
                onModuleLoad2();
            }
        });
    }

    private void onModuleLoad2() {
        RootPanel.get().add(UIRootPanel.get().getContent());
//        fuck();
    }


    private void fuck() {
//        System.out.println("fromX=" + from.x + " fromY=" + from.y + " toX=" + to.x + " toY=" + to.y);
//        if(from.x == to.x && from.y == to.y)
//            return;
            Canvas canvas = Canvas.createIfSupported();
            canvas.setPixelSize(400,    400);
//        canvas.setCoordinateSpaceHeight(0);
//        canvas.setCoordinateSpaceWidth(10);

            RootPanel.get().getElement().appendChild(canvas.getElement());
            Context2d context = canvas.getContext2d();

            context.beginPath();
            context.setLineWidth(1);
//            context.moveTo(0, 0);
            context.lineTo(150, 150);
//            context.moveTo(150,150);
//            context.lineTo(359, 309);
//            context.moveTo(359, 309);
            context.stroke();
//        context.closePath();

        }
    }


