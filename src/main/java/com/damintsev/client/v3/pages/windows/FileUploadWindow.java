package com.damintsev.client.v3.pages.windows;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

/**
* User: Damintsev Andrey
* Date: 15.10.13
* Time: 23:18
*/
public class FileUploadWindow extends Window {

    private final static String IMG_URL = "image?imageId=";

    private static FileUploadWindow instance;

    public static FileUploadWindow getInstance() {
        if(instance == null)
            instance = new FileUploadWindow();
        return instance;
    }

    private Image newImage;
    private FormPanel form;

    protected FileUploadWindow() {

        BorderLayoutContainer container = new BorderLayoutContainer();

        setPixelSize(650,450);
        VerticalLayoutContainer verticalPanel = new VerticalLayoutContainer();
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.add(verticalPanel);
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
        comboBox.setEditable(false);

        FieldLabel label = new FieldLabel(comboBox, "Тип");
        label.setLabelWidth(30);
        verticalPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        form = new FormPanel();
        verticalPanel.add(form, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        container.setWestWidget(con, new BorderLayoutContainer.BorderLayoutData(200));
//        form.add(panel);
//        form.setEncoding(FormPanel.Encoding.MULTIPART);
//        form.setMethod(FormPanel.Method.POST);
////        form.setAction("uploader.fileUpload");
//
        FileUploadField fileUploadField = new FileUploadField();
        fileUploadField.setName("upload");
////        DivElement wrapper = Document.getInstance().createDivElement();
////        wrapper.appendChild(fileUploadField.getElement());
////        TextButton button = new TextButton("adasd");
////        wrapper.appendChild(bu)
//        label = new FieldLabel(fileUploadField, "Изображение");
        verticalPanel.add(fileUploadField, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
//
//        newImage = new Image("getImage");
//        Element table = DOM.createTable();
//        table.getStyle().setWidth(100, Style.Unit.PCT);
//        Element tr = DOM.createTR();
//        Element td = DOM.createTD();
//        td.getStyle().setWidth(50, Style.Unit.PCT);
//        td.appendChild(newImage.getElement());
//        td.setInnerText("Новое изображение");
//        tr.appendChild(td);
//        final Image oldImage = new Image();
//
////        oldImage.setWidth("inherit");
//        td = DOM.createTR();
//        td.getStyle().setWidth(50, Style.Unit.PCT);
//        td.setInnerText("Существующее изображение");
//        td.appendChild(oldImage.getElement());
//        tr.appendChild(td);
//
//        table.appendChild(tr);
//
//        comboBox.addSelectionHandler(new SelectionHandler<Types>() {
//            @Override
//            public void onSelection(SelectionEvent<Types> event) {
//                oldImage.setUrl(IMG_URL + event.getSelectedItem().eng);
//            }
//        });
//        DOM.appendChild(panel.getElement(), table);
//
//        con.addButton(new TextButton("Загрузить на сервер", new SelectEvent.SelectHandler() {
//            public void onSelect(SelectEvent event) {
//                if (!comboBox.validate()) return;
//                form.setAction("fileUpload/upload");//?type=" + comboBox.getValue().eng);
//                form.submit();
//            }
//        }));
//        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
//            public void onSelect(SelectEvent event) {
//                Service.instance.saveImage(comboBox.getValue().eng, new AsyncCallback<Void>() {
//                    public void onFailure(Throwable caught) {
//                        //Todo change body of implemented methods use File | Settings | File Templates.
//                    }
//
//                    public void onSuccess(Void result) {
//                        Dialogs.message("Изображение успешно сохранено", new Runnable() {
//                            public void run() {
//                                Scheduler.get().scheduleDeferred(new Command() {
//                                    public void execute() {
//                                        MonitoringFrame.reload();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        }));
        addButton(new TextButton("Отменить", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        }));


//        form.addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
//            public void onSubmitComplete(SubmitCompleteEvent event) {
//                newImage.setUrl("image?type=tmp");
//            }
//        });
//        form.setWidget(con);

        Image image = new Image("/web/img/cloud.png");
        image.getElement().setClassName("test");
        HTML html = new HTML("<div> " + image.getElement().toString() +  " </div>");

        container.setCenterWidget(html);

        setWidget(container);


//        com.google.gwt.dom.client.Element element = Document.get().getElementsByTagName("head").getItem(0);
//        assert element != null : "HTML Head element required";
//        HeadElement head = HeadElement.as(element);
//
//        ScriptElement script = Document.get().createScriptElement();
//        script.setAttribute("language", "javascript");
//
//        script.setSrc("web/js/jquery.min.js");
//        head.appendChild(script);
//
//        script = Document.get().createScriptElement();
//        script.setAttribute("language", "javascript");
//        script.setSrc("web/js/jquery.imgareaselect.min.js");
//        head.appendChild(script);
    }

    @Override
    public void show() {
//        super.show();
        load();
    }

    private class Types {
        String rus;
        String eng;

        Types(String rus, String eng) {
            this.rus = rus;
            this.eng = eng;
        }
    }

    public static native void load()/*-{
//        $wnd.jQuery.noConflict();
//        $wnd.jQuery($wnd.document.getElementsByClassName('test')).imgAreaSelect({ maxWidth: 400, maxHeight: 450, handles: true });
        $wnd.openWindow();

    }-*/;
}


/*
   package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.service.Service;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;


* User: Damintsev Andrey
* Date: 15.10.13
* Time: 23:18

public class FileUploadWindow extends Window {

    interface MyBinder extends UiBinder<Widget, FileUploadField> {
    }

    private static FileUploadWindow instance;

    public static FileUploadWindow getInstance() {
//        if(instance == null)
        instance = new FileUploadWindow();
        return instance;
    }

    private Image newImage;
    private FormPanel form;

    protected FileUploadWindow() {
        setPixelSize(650,450);
        setModal(true);
        setHeadingText("Загрузка изображений");
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
        comboBox.setEditable(false);

        FieldLabel label = new FieldLabel(comboBox, "Тип");
        label.setLabelWidth(100);
        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        form = new FormPanel();
//        panel.add(form);
//        form.add(panel);
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
//        form.setAction("uploader.fileUpload");

        FileUploadField fileUploadField = new FileUploadField();
        fileUploadField.setName("upload");
//        DivElement wrapper = Document.getInstance().createDivElement();
//        wrapper.appendChild(fileUploadField.getElement());
//        TextButton button = new TextButton("adasd");
//        wrapper.appendChild(bu)
        label = new FieldLabel(fileUploadField, "Изображение");
        panel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        newImage = new Image("getImage");
        Element table = DOM.createTable();
        table.getStyle().setWidth(100, Style.Unit.PCT);
        Element tr = DOM.createTR();
        Element td = DOM.createTD();
        td.getStyle().setWidth(50, Style.Unit.PCT);
        td.appendChild(newImage.getElement());
        td.setInnerText("Новое изображение");
        tr.appendChild(td);
        final Image oldImage = new Image();

//        oldImage.setWidth("inherit");
        td = DOM.createTR();
        td.getStyle().setWidth(50, Style.Unit.PCT);
        td.setInnerText("Существующее изображение");
        td.appendChild(oldImage.getElement());
        tr.appendChild(td);

        table.appendChild(tr);

        comboBox.addSelectionHandler(new SelectionHandler<Types>() {
            @Override
            public void onSelection(SelectionEvent<Types> event) {
                oldImage.setUrl("image?type=" + event.getSelectedItem().eng);
            }
        });
        DOM.appendChild(panel.getElement(), table);

        con.addButton(new TextButton("Загрузить на сервер", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                if (!comboBox.validate()) return;
                form.setAction("fileUpload/upload");//?type=" + comboBox.getValue().eng);
                form.submit();
            }
        }));
        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Service.instance.saveImage(comboBox.getValue().eng, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        //Todo change body of implemented methods use File | Settings | File Templates.
                    }

                    public void onSuccess(Void result) {
                        Dialogs.message("Изображение успешно сохранено", new Runnable() {
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
        con.addButton(new TextButton("Отменить", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        }));
        form.addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {
                newImage.setUrl("image?type=tmp");
            }
        });
        form.setWidget(con);


//        ToolBar toolBar =new ToolBar();
        HorizontalLayoutContainer hor = new HorizontalLayoutContainer();

        hor.add(fileUploadField, new HorizontalLayoutContainer.HorizontalLayoutData(0.8,1.0,new Margins(5)));
        hor.add(new TextButton("Загрузить на сервер", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                if (!comboBox.validate()) return;
                form.setAction("fileUpload/upload");//?type=" + comboBox.getValue().eng);
                form.submit();
            }
        }), new HorizontalLayoutContainer.HorizontalLayoutData(0.2, 22, new Margins(5)));
//        setWidget(form);
        BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
        borderLayoutContainer.setNorthWidget(hor, new BorderLayoutContainer.BorderLayoutData(100));
        borderLayoutContainer.setCenterWidget(new HTML(getTemplate()));
//        setWidget(new HTML(getTemplate()));
        setWidget(borderLayoutContainer);
    }

    @Override
    public void show() {
        super.show();
    }

    private class Types {
        String rus;
        String eng;

        Types(String rus, String eng) {
            this.rus = rus;
            this.eng = eng;
        }
    }

}

 */