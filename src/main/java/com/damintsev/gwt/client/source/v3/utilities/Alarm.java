package com.damintsev.gwt.client.source.v3.utilities;

import com.damintsev.gwt.client.source.EventBus;
import com.damintsev.gwt.client.source.event.AlarmEvent;
import com.damintsev.gwt.client.source.event.AlarmEventHandler;
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

    private boolean playing;

    private AbsolutePanel panel;
    private Window window;
    private TextArea text;
    private AlarmEventHandler handler;

    private Alarm() {
        handler = new AlarmEventHandler() {
            @Override
            public void onAlarmEdit(AlarmEvent event) {
                if(window == null) window = initWindow(); //don`t move this because when its creating shadow shows at the screen
                text.setText("Произошла ошибка на маршруте: " + event.getItem().getName());
                if(!playing) {
                    startSound();
                    playing = true;
                }
                if(!window.isVisible())
                    window.show();
            }
        };
    }

    public void alarmOn() {
        loadSounds();
        EventBus.get().addHandler(AlarmEvent.TYPE, handler);
    }

    public void alarmOff() {
        EventBus.get().removeHandler(AlarmEvent.TYPE, handler);
    }

    public void setParentElement(final AbsolutePanel panel) {
        this.panel = panel;
    }

    private Window initWindow() {
        final Window window = new Window();
        window.setDraggable(true);
        window.add(text = new TextArea());
        window.setPixelSize(250, 150);
        window.addButton(new TextButton("Закрыть", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                stopSound();
                playing = false;
                window.hide();
            }
        }));
        window.addBeforeHideHandler(new BeforeHideEvent.BeforeHideHandler() {
            @Override
            public void onBeforeHide(BeforeHideEvent event) {
                playing = false;
                stopSound();
            }
        });
        panel.add(window);
        return window;
    }

    public static native void stopSound()/*-{
        $wnd.sound.stop();
    }-*/;

    public static native void startSound()/*-{
        $wnd.sound.play();
    }-*/;

    public static native void loadSounds()/*-{
        if($wnd.sound == null) {
            $wnd.initSound();
        }
    }-*/;
}
