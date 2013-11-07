package com.damintsev.client.v3.utilities;

import com.damintsev.common.beans.TaskState;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

/**
 * User: Damintsev Andrey
 * Date: 07.11.13
 * Time: 23:01
 */
public class StatusToolTip {

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

    private Renderer renderer = GWT.create(Renderer.class);

    public ToolTipConfig createToolTip() {
        ToolTipConfig config = new ToolTipConfig();
        config.setBodyHtml("Prints the current document");
        config.setTitleHtml("Template Tip");
        config.setMouseOffset(new int[]{0, 0});
        config.setAnchor(Style.Side.LEFT);
        config.setRenderer(renderer);
        config.setCloseable(true);
        config.setTrackMouse(true);
        config.setMaxWidth(415);
        TaskState state = new TaskState();
        state.setMessage("XYU PIZDA");
        config.setData(state);
        return config;
    }
}
