package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.v3.pages.windows.LabelWindow;
import com.damintsev.common.utils.Position;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 21:59
 */
public class UILabel extends UIItem {

    private Label label;
    private com.damintsev.common.uientity.Label itemLabel;

    public UILabel(Item item) {
        super(item);
        this.itemLabel = (com.damintsev.common.uientity.Label) item;
        initWidget(widget());
    }

    @Override
    protected int getLeft() {
        return label.getAbsoluteLeft();
    }

    @Override
    protected int getTop() {
        return label.getAbsoluteTop();
    }

    @Override
    public Position getCenterPosition() {
        return null;
    }

    @Override
    protected int getWidth() {
        return 0;
    }

    @Override
    protected int getHeight() {
        return 0;
    }

    @Override
    public Widget widget() {
        label = new Label();
//        System.out.println("id=" + getName() + " h=" + itemLabel.getHasImage());
        if(itemLabel.getHasImage()) {
            Image image = initImage();
            label.getElement().appendChild(image.getElement());
        }
        Label nameLabel = new Label(getName());
        nameLabel.getElement().setInnerHTML(getName().replace("\n", "<br>"));
        nameLabel.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
        nameLabel.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
        nameLabel.setStyleName("tooltip");
        label.getElement().appendChild(nameLabel.getElement());
        return label;
    }

    @Override
    public void openEditor(Runnable runnable) {
        LabelWindow.get().show(item.getId(), runnable);
    }

    private Image initImage() {
        Image image = new Image();
        image.setUrl("api/image?imageId=" + itemLabel.getImageId());
        return image;
    }
}
