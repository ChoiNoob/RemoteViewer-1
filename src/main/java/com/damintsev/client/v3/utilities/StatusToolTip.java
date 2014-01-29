package com.damintsev.client.v3.utilities;

import com.damintsev.common.uientity.TaskState;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

/**
 * User: Damintsev Andrey
 * Date: 07.11.13
 * Time: 23:01
 */
public class StatusToolTip extends ToolTipConfig {

    public interface Renderer extends ToolTipConfig.ToolTipRenderer<TaskState>, XTemplates {

        @Override
        @XTemplate(source = "template.html")
        public SafeHtml renderToolTip(TaskState data);
    }

    private static StatusToolTip instance;

    public static StatusToolTip getInstance() {
        if (instance == null) instance = new StatusToolTip();
        return instance;
    }

    public StatusToolTip() {
        super();
        setMouseOffset(new int[]{0, 0});
        setAnchor(Style.Side.LEFT);
        setRenderer(renderer);
//        setCloseable(true);
        setTrackMouse(false);
        setMaxWidth(415);
        TaskState state = new TaskState();
        state.setMessage("");
        setData(state);
    }

    private Renderer renderer = GWT.create(Renderer.class);
//    private ToolTipConfig config;

//    public ToolTipConfig createToolTip() {
//
//        config = new ToolTipConfig();
////        config.setBodyHtml("Prints the current document");
////        config.setTitleHtml("Template Tip");
//        config.setMouseOffset(new int[]{0, 0});
//        config.setAnchor(Style.Side.LEFT);
//        config.setRenderer(renderer);
//        config.setCloseable(true);
//        config.setTrackMouse(false);
//        config.setMaxWidth(415);
//        TaskState state = new TaskState();
//        state.setMessage("");
//        config.setData(state);
//        return config;
//    }

    public void setMessage(TaskState state) {
//        TaskState state = new TaskState();
//        Dialogs.alert("asd" + state.getMessage());
        if (state.getMessage() == null) state.setMessage("");
//        state.setMessage("XYU PIZDA");

        setData(state);
//        renderer.renderToolTip(state);
    }
}
