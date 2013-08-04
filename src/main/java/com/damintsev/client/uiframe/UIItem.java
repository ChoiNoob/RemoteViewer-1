package com.damintsev.client.uiframe;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public abstract class UIItem extends Image {

    public abstract void getPosition();

    public abstract String getName();

    @Override
    public abstract Widget asWidget();


}
