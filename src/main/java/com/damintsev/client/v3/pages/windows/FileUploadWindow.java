package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.EventBus;
import com.damintsev.common.event.FileUploadEvent;
import com.damintsev.common.event.FileUploadHandler;
import com.damintsev.common.utils.Dialogs;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.SpinnerField;

/**
* User: Damintsev Andrey
* Date: 15.10.13
* Time: 23:18
*/
public class FileUploadWindow extends Window {

    private static FileUploadWindow instance;

    public static FileUploadWindow getInstance() {
        if(instance == null)
            instance = new FileUploadWindow();
        return instance;
    }

    private Image image;
    private SpinnerField<Integer> widthField;
    private SpinnerField<Integer> heightField;

    protected FileUploadWindow() {
        setPixelSize(850,500);
        setHeadingText("Изображения по умолчанию");

        BorderLayoutContainer container = new BorderLayoutContainer();

        ContentPanel editorContainer = new ContentPanel();
        editorContainer.setHeaderVisible(false);
        editorContainer.setBodyStyle("padding: 5px");
        editorContainer.setHeight(300);
        VerticalLayoutContainer editorPanel = new VerticalLayoutContainer();
        editorContainer.setWidget(editorPanel);

        final SimpleComboBox<Types> imageSelector = new SimpleComboBox<Types>(new LabelProvider<Types>() {
            public String getLabel(Types item) {
                return item.rus;
            }
        });
        imageSelector.add(new Types(1L, "Станция", "station"));
        imageSelector.add(new Types(2L, "Маршрут", "task"));
        imageSelector.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        imageSelector.setAllowBlank(false);
        imageSelector.setEditable(false);

        FieldLabel label = new FieldLabel(imageSelector, "Тип");
        editorPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        container.setWestWidget(editorContainer, new BorderLayoutContainer.BorderLayoutData(250));
        setWidget(container);

        widthField = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        widthField.setAllowNegative(false);
        widthField.setMaxValue(500);
        widthField.setMinValue(50);
        widthField.setAllowBlank(false);
        widthField.setValue(200);
        label = new FieldLabel(widthField, "Ширина");
        editorPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        heightField = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        heightField.setAllowNegative(false);
        heightField.setMaxValue(500);
        heightField.setMinValue(50);
        heightField.setAllowBlank(false);
        heightField.setValue(200);
        label = new FieldLabel(heightField, "Высота");
        editorPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        final ContentPanel imagePanel = new ContentPanel();
        imagePanel.setHeaderVisible(false);

        VerticalLayoutContainer imageContainer = new VerticalLayoutContainer();

        String formHtml = "<div>" +
                "<form id=\"uploadForm\" enctype=\"multipart/form-data\">" +
                "<input id=\"upload\" type=\"file\" name=\"file\" data-url=\"api/upload/image\" multiple/>" +
                "</form>" +
                "</div>";
        HTML html = new HTML(formHtml);

        HBoxLayoutContainer horizontalLayoutContainer = new HBoxLayoutContainer();
        horizontalLayoutContainer.setPadding(new Padding(5));
        horizontalLayoutContainer.add(html);
        BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        horizontalLayoutContainer.add(new Label(), flex);

        imageContainer.add(horizontalLayoutContainer);


        image = new Image();

        imageContainer.add(image, new VerticalLayoutContainer.VerticalLayoutData());

        imagePanel.setWidget(imageContainer);
        container.setCenterWidget(imagePanel);
        addCallbackListener();

        EventBus.get().addHandler(FileUploadEvent.TYPE, new FileUploadHandler() {
            @Override
            public void onFileUpload(FileUploadEvent event) {
                Dialogs.alert("id=" + event.getFileId() + " width=" + event.getWidth() + " height=" + event.getHeight());
                image.setUrl("api/image/temporary?imageId=" + event.getFileId());
                image.setWidth("" +event.getWidth());
                image.setHeight("" + event.getHeight());
                widthField.setValue(event.getWidth());
                heightField.setValue(event.getHeight());
            }
        });
        addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                  //todo
            }
        }));

    }

    @Override
    public void show() {
        super.show();
        binds();
    }

    private static class Types {
        String rus;
        String eng;
        Long id;

        Types(Long id, String rus, String eng) {
            this.id = id;
            this.rus = rus;
            this.eng = eng;
        }
    }

    public static native void binds()/*-{
        $wnd.parent.bindd();
    }-*/;

    public static native void addCallbackListener()/*-{
        $wnd.jsniCallback = function (id, width, height) {
            @com.damintsev.client.v3.pages.windows.FileUploadWindow::fileCallback(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)(id, width, height)
        };
    }-*/;

    public static void fileCallback(Integer id, Integer width, Integer height) {
        EventBus.get().fireEvent(new FileUploadEvent(id, width, height));
    }

}
