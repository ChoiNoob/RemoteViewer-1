package com.damintsev.client.v3.uiitems;

import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sun.javafx.property.adapter.PropertyDescriptor;

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
public class FileUploadExample extends Window{

    private static FileUploadExample instance;

    public static FileUploadExample getInstance() {
        if(instance == null) instance = new FileUploadExample();
        return instance;
    }

    protected FileUploadExample() {
        setPixelSize(300,300);
        ContentPanel panel = new ContentPanel();
        panel.setHeaderVisible(false);
        final FormPanel form = new FormPanel();
        panel.add(form);
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
        form.setAction("uploader.fileUpload");

        FileUploadField field = new FileUploadField();
        form.add(field);

        panel.addButton(new TextButton("Submit", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                System.out.println("fuck!");
                form.submit();
                System.out.println("fuck!2");
            }
        }));

        setWidget(panel);
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