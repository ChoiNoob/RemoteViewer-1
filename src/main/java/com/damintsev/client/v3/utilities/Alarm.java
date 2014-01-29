package com.damintsev.client.v3.utilities;

import com.damintsev.client.EventBus;
import com.damintsev.common.event.AlarmEvent;
import com.damintsev.common.event.AlarmEventHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.BeforeHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * @author Damintsev Andrey
 *         28.01.14.
 */
public class Alarm {

    private static Alarm instance;

    public static Alarm getInstance() {
        if(instance == null) instance = new Alarm();
        return instance;
    }

    private AbsolutePanel panel;
    private Window window;
    private TextArea text;
    private boolean playng;
    private AlarmEventHandler handler;

    private Alarm() {
        handler = new AlarmEventHandler() {
            @Override
            public void onAlarmEdit(AlarmEvent event) {
                if(window == null) initWindow();
                text.setText("Произошла ошибка на маршруте: " + event.getItem().getName());
                if(!playng) {
                    startSound();
                    playng = true;
                }
                if(!window.isVisible())
                    window.show();
            }
        };

    }

    public void alarmOn() {
        EventBus.get().addHandler(AlarmEvent.TYPE, handler);
    }

    public void alarmOff() {
        EventBus.get().removeHandler(AlarmEvent.TYPE, handler);
    }

    public void setParentElement(final AbsolutePanel panel) {
        this.panel = panel;
    }

    private void initWindow() {
        window = new Window();
//todo change to center window top: 150px        window.setPosition(window.get, 200);
        //todo !! window.getElement().getStyle().setMarginTop(200, Style.Unit.PX);
        window.setDraggable(true);
        window.add(text = new TextArea());
        window.setPixelSize(250, 150);
        window.addButton(new TextButton("Закрыть", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                stopSound();
                playng = false;
                window.hide();
            }
        }));
        window.addBeforeHideHandler(new BeforeHideEvent.BeforeHideHandler() {
            @Override
            public void onBeforeHide(BeforeHideEvent event) {
                playng = false;
                stopSound();
            }
        });
        panel.add(window);
    }

    public static native void stopSound()/*-{
        $wnd.sound.stop();
    }-*/;

    public static native void startSound()/*-{
        $wnd.sound.play();
    }-*/;

    public static native void isRuning()/*-{

    }-*/;
}
