package com.damintsev.client.v3.utilities;

import com.google.gwt.safehtml.shared.SafeUri;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.widget.core.client.button.ToggleButton;

/**
 * User: adamintsev
 * Date: 29.01.14
 * //todo написать комментарии
 */
public class UIButton {

    public static ToggleButton createToggleToolButton(final String url, int width, int height) {
        ToggleButton button = new ToggleButton("");
        button.setIcon(IconHelper.getImageResource(new SafeUri() {
            @Override
            public String asString() {
                return url;
            }
        }, width, height));
        button.setIconAlign(ButtonCell.IconAlign.RIGHT);
        return button;
    }

    public static ToggleButton createToggleToolButton(String url) {
        return createToggleToolButton(url, 15, 15);
    }
}
