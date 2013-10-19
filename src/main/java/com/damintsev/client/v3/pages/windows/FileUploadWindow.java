package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.service.Service;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

/**
* User: Damintsev Andrey
* Date: 15.10.13
* Time: 23:18
*/
public class FileUploadWindow extends Window{

    private static FileUploadWindow instance;

    public static FileUploadWindow getInstance() {
        if(instance == null) instance = new FileUploadWindow();
        return instance;
    }

    private Image newImage;

    protected FileUploadWindow() {
        setPixelSize(550,450);
        VerticalLayoutContainer panel = new VerticalLayoutContainer();
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.add(panel);
        con.setBodyStyle("padding: 5px");
        final SimpleComboBox<Types> comboBox = new SimpleComboBox<Types>(new LabelProvider<Types>() {
            public String getLabel(Types item) {
                return item.rus;
            }
        });
        comboBox.add(new Types("Станция", "station"));
        comboBox.add(new Types("Маршрут", "task"));
        comboBox.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        comboBox.setAllowBlank(false);

        FieldLabel label = new FieldLabel(comboBox, "Тип");
        label.setLabelWidth(100);
        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        final FormPanel form = new FormPanel();
//        panel.add(form);
//        form.add(panel);
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
//        form.setAction("uploader.fileUpload");

        FileUploadField fileUploadField = new FileUploadField();
        fileUploadField.setName("upload");
        label = new FieldLabel(fileUploadField, "Изображение");
        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        newImage = new Image("getImage");
//        label = new FieldLabel(newImage);
//        label.setWidth(10);
        Element table = DOM.createTable();
        table.getStyle().setWidth(100, Style.Unit.PCT);
        Element tr = DOM.createTR();
        Element td = DOM.createTD();
        td.getStyle().setWidth(50, Style.Unit.PCT);
//        td.getStyle().setHeight(200, Style.Unit.PX);
        td.appendChild(newImage.getElement());
        tr.appendChild(td);
//        hor.add(newImage);
        Image oldImage = new Image("image?type=station");
        oldImage.setWidth("inherit");
        td = DOM.createTR();
        td.getStyle().setWidth(50, Style.Unit.PCT);
//        td.getStyle().setHeight(50, Style.Unit.PC);
        td.appendChild(oldImage.getElement());
        tr.appendChild(td);

        table.appendChild(tr);
//        label = new FieldLabel(oldImage);
//        hor.add(oldImage);
//        Label test = new Label("");
//        test.getElement().appendChild(table);
//        label = new FieldLabel();
//        label.setText("");
//        label.getElement().appendChild(table);
//                label.setLabelWidth(1);
        DOM.appendChild(panel.getElement(), table);

//        panel.add(label);

        con.addButton(new TextButton("Submit", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                if (!comboBox.validate()) return;
                form.setAction("fileUpload/upload");//?type=" + comboBox.getValue().eng);
                form.submit();
                System.out.println("fuck!2");
            }
        }));
        con.addButton(new TextButton("Save", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Service.instance.saveImage(comboBox.getValue().eng, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        //Todo change body of implemented methods use File | Settings | File Templates.
                    }

                    public void onSuccess(Void result) {
                        Dialogs.confirm("Suscsess", new Runnable() {
                            public void run() {
                                Scheduler.get().scheduleDeferred(new Command() {
                                    public void execute() {
                                        MonitoringFrame.reload();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }));
        form.addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {
                newImage.setUrl("image?type=tmp");

            }
        });
        form.setWidget(con);
        setWidget(form);
    }

    class Types {
        String rus;
        String eng;

        Types(String rus, String eng) {
            this.rus = rus;
            this.eng = eng;
        }
    }
}