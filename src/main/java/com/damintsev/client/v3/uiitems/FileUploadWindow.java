package com.damintsev.client.v3.uiitems;

import com.damintsev.client.service.Service;
import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
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
import com.sencha.gxt.widget.core.client.info.Info;

/**
* User: Damintsev Andrey
* Date: 15.10.13
* Time: 23:18
*/
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.FormPanel;
//import com.sencha.gxt.widget.core.client.box.MessageBox;
//import com.sencha.gxt.widget.core.client.form.FileUploadField;
//
//import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ChangeEvent;
//import com.google.gwt.event.dom.client.ChangeHandler;
//import com.google.gwt.user.client.ui.IsWidget;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.Widget;
//import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
//import com.sencha.gxt.data.shared.ListStore;
//import com.sencha.gxt.widget.core.client.FramedPanel;
//import com.sencha.gxt.widget.core.client.box.MessageBox;
//import com.sencha.gxt.widget.core.client.button.TextButton;
//import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
//import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
//import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
//import com.sencha.gxt.widget.core.client.event.SelectEvent;
//import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
//import com.sencha.gxt.widget.core.client.form.ComboBox;
//import com.sencha.gxt.widget.core.client.form.FieldLabel;
//import com.sencha.gxt.widget.core.client.form.FileUploadField;
//import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
//import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
//import com.sencha.gxt.widget.core.client.form.TextField;
//import com.sencha.gxt.widget.core.client.info.Info;
//
public class FileUploadWindow extends Window{

    private static FileUploadWindow instance;

    public static FileUploadWindow getInstance() {
        if(instance == null) instance = new FileUploadWindow();
        return instance;
    }

    Image image;

    protected FileUploadWindow() {
        setPixelSize(400,400);
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

        image = new Image("getImage");
        label = new FieldLabel(image);
//        label.setWidth(10);
        panel.add(label);

        con.addButton(new TextButton("Submit", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                if(!comboBox.validate())return;
                form.setAction("fileUpload/upload");//?type=" + comboBox.getValue().eng);
                form.submit();
                System.out.println("fuck!2");
            }
        }));
        con.addButton(new TextButton("Save", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Service2.database.saveImage(comboBox.getValue().eng, new AsyncCallback<Void>() {
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
//                Dialogs.alert("FUCK!!!!");
                image.setUrl("image?type=tmp");

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
//
//    public static final float SIZE = 130;
//
//    @Override
//    protected void onRender(Element parent, int index) {
//        super.onRender(parent, index);
//
//        setLayout(new FitLayout());
//
//        final VerticalPanel uploaderPanel = new VerticalPanel();
//
//        uploaderPanel.setScrollMode(Scroll.AUTO);
//
//        uploaderPanel.setSpacing(10);
//
//        uploaderPanel.setHorizontalAlign(HorizontalAlignment.RIGHT);
//
//        MultiUploader uploader = new MultiUploader(FileInputType.LABEL);
//        // we can change the internationalization by creating custom Constants
//        // file
//
//        uploader.setAvoidRepeatFiles(false);
//
//        uploader.setServletPath("uploader.fileUpload");
//        uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
//            public void onFinish(IUploader uploader) {
//
//                if (uploader.getStatus() == Status.SUCCESS) {
//
//                    String response = uploader.getServerResponse();
//
//                    if (response != null) {
//                        Document doc = XMLParser.parse(response);
//                        String message = Utils.getXmlNodeValue(doc, "message");
//                        String finished = Utils
//                                .getXmlNodeValue(doc, "finished");
//
//                        Window.alert("Server response: \n" + message + "\n"
//                                + "finished: " + finished);
//                    } else {
//                        Window.alert("Unaccessible server response");
//                    }
//
//                    // uploader.reset();
//                } else {
//                    Window.alert("Uploader Status: \n" + uploader.getStatus());
//                }
//
//            }
//        });
//
//        uploaderPanel.add(uploader);
//
//        Button closeButton = new Button("Close",
//                new SelectionListener<ButtonEvent>() {
//
//                    public void componentSelected(ButtonEvent ce) {
//                        EP.closeUploader();
//                    }
//
//                });
//
//        uploaderPanel.add(closeButton);
//
//        add(uploaderPanel);
//    }
//
//}