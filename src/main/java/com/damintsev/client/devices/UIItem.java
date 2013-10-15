package com.damintsev.client.devices;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.utils.Position;
import com.damintsev.utils.Utils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 03.08.13
 * Time: 2:09
 */
public class UIItem extends Label {

    protected Item item;
    protected TaskState taskState;
    protected transient Image image;

    public UIItem(){ }

    public UIItem(Item item) {
        super();
        this.item = item;
        taskState = new TaskState();
        init();
    }

    public void savePosition() {
        item.getPosition().x = getAbsoluteLeft();
        item.getPosition().y = getAbsoluteTop();
    }

    private void init() {
        setHorizontalAlignment(ALIGN_CENTER);
       if(getImage() != null) {
            image = new Image(getImage());
            getElement().appendChild(image.getElement());
       }
       Label label = new Label(getName());
        if(getName() != null)
            getElement().appendChild(label.getElement());
        label.setStyleName("tooltip");
//        super.addDoubleClickHandler(new DoubleClickHandler() {
//            public void onDoubleClick(DoubleClickEvent event) {
//                if (getDeviceType() == DeviceType.STATION) return;
//                BusyChannelWindow panel = new BusyChannelWindow();
//                panel.show(UIItem.this);
//            }
//        });
    }

    public Position getCenterPosition() {
        int x = getAbsoluteLeft() + getWidth() / 2;
        int y = getAbsoluteTop() + getHeight() / 2;
        return new Position(x, y);
    }

    public Position getPosition() {
        return item.getPosition();
    }

    public ImageResource getImage() {
//        return Utils.getImage("cloud");//todo customize
        switch (item.getType()) {
            case IP:
            case TELNET:
            case ISDN:
                return Utils.getImage("cloud");
            case LABEL:
                return null;
            default:
                return Utils.getImage("hipath");
        }
    }

    public String getName() {
        return item.getName();
    }

    public int getWidth() {
        return image==null?super.getAbsoluteTop():image.getWidth();
    }

    public int getHeight() {
        return image==null?super.getOffsetHeight():image.getHeight();
    }

    public String getId() {
        return item.getStringId();
    }

    public Station getStation() {
        return item.getStation();
    }

    public void setLabelColor() { //todo really need ?!
//        label.getElement().getStyle().setBackgroundColor(data.getStatus().getColor());
    }

    public void redraw() {
        System.out.println("redraw");
//        label.getElement().setInnerText(getName());
////          label = new Label(getName());
//        getElement().removeChild(label.getElement());
//        label = new Label(getName());
//        getElement().appendChild(label.getElement());
    }

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

    //todo сделть абстранктным и переопределить нафиг
    public void openEditor(Runnable runnable) {

    }
}
