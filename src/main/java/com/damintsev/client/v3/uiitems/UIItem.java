package com.damintsev.client.v3.uiitems;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.TaskState;
import com.damintsev.common.utils.Position;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public abstract class UIItem extends Widget implements IsWidget {

    private Widget widget;
    protected Item item;
    protected TaskState taskState;
    protected Image image;

    @Override
    public Widget asWidget() {
        if (widget == null) widget = widget();
        return widget;
    }

    public UIItem(Item item) {
        this.item = item;
        taskState = new TaskState();
    }

        public void savePosition() {
        item.getPosition().x = getLeft();
        item.getPosition().y = getTop();
    }

    protected abstract Widget widget();
    protected abstract int getLeft();
    protected abstract int getTop();
    protected abstract int getWidth();
    protected abstract int getHeight();
//
//    private void init() {
//        setHorizontalAlignment(ALIGN_CENTER);
//       if(getImage() != null) {
////            image = new Image(getImage());
//            getElement().appendChild(getImage().getElement());
//       }
//       Label label = new Label(getName());
//        if(getName() != null)
//            getElement().appendChild(label.getElement());
//        label.setStyleName("tooltip");
////        super.addDoubleClickHandler(new DoubleClickHandler() {
////            public void onDoubleClick(DoubleClickEvent event) {
////                if (getDeviceType() == DeviceType.STATION) return;
////                BusyChannelWindow panel = new BusyChannelWindow();
////                panel.show(UIItem.this);
////            }
////        });
//    }

    public Position getCenterPosition() {
        int x = getLeft() + getWidth() / 2;
        int y = getTop() + getHeight() / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
        return item.getPosition();
    }

    protected String getName() {
        return item.getName();
    }

//    public String getName() {
//        return item.getName();
//    }

//    public int getWidth() {
//        return getImage() == null ? super.getTop() : getImage().getWidth();
//    }
//
//    public int getHeight() {
//        return getImage() == null ? super.getOffsetHeight() : getImage().getHeight();
//    }

    public String getId() {
        return item.getStringId();
    }

    public Station getStation() {
        return item.getStation();
    }

//    public void setLabelColor() { //todo really need ?!
////        label.getElement().getStyle().setBackgroundColor(data.getStatus().getColor());
//    }

//    public void redraw() {
//        System.out.println("redraw");
////        label.getElement().setInnerText(getName());
//////          label = new Label(getName());
////        getElement().removeChild(label.getElement());
////        label = new Label(getName());
////        getElement().appendChild(label.getElement());
//    }

    public boolean haveChildrens() {
        return getId().startsWith("s");
    }

    public boolean isChild(UIItem child) {
        return child.getId().startsWith("s");
    }

    public String getParentId() {
        return item.getParentId();
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState status) {
        this.taskState = status;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public abstract void openEditor(Runnable runnable);
}
