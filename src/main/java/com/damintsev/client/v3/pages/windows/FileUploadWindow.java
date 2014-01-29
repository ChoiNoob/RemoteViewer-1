package com.damintsev.client.v3.pages.windows;

import com.google.gwt.dom.client.Style;
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
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitEvent;
import com.sencha.gxt.widget.core.client.form.*;

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

    private VerticalLayoutContainer imageContainer;
    private Image image;
    private Image newImage;
    private FormPanel form;

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

        SpinnerField<Integer> widthField = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        widthField.setAllowNegative(false);
        widthField.setMaxValue(500);
        widthField.setMinValue(50);
        widthField.setAllowBlank(false);
        widthField.setValue(200);
        label = new FieldLabel(widthField, "Ширина");
        editorPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        SpinnerField<Integer> heightField = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        heightField.setAllowNegative(false);
        heightField.setMaxValue(500);
        heightField.setMinValue(50);
        heightField.setAllowBlank(false);
        heightField.setValue(200);
        label = new FieldLabel(heightField, "Высота");
        editorPanel.add(label, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        final ContentPanel imagePanel = new ContentPanel();
        imagePanel.setHeaderVisible(false);

        imageContainer = new VerticalLayoutContainer();


        form = new FormPanel();
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
        form.addSubmitHandler(new SubmitEvent.SubmitHandler() {
            @Override
            public void onSubmit(SubmitEvent event) {
              //todo  imagePanel.mask();
            }
        });
        form.addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                imagePanel.unmask();
                image.setUrl("image?id=0");
            }
        });


        FileUploadField fileUploadField = new FileUploadField();
        fileUploadField.setName("text");
        fileUploadField.setWidth(250);

        label = new FieldLabel(fileUploadField, "Изображение");
        label.setWidth(250);

        HTML html = new HTML(
                "<form id=\"ImageUpload\" name=\"ImageUpload\" target=\"iframe\" action=\"/upload?imageId=0\" method=\"POST\" enctype=\"multipart/form-data\">\n" +
                "<div>\n" +
                "Select images:  \n" +
                "<input type=\"file\" id=\"file\" name=\"file\"/> \n" +
                "</div>\n" +
                "</form> ");
        HBoxLayoutContainer horizontalLayoutContainer = new HBoxLayoutContainer();
        horizontalLayoutContainer.setPadding(new Padding(5));
        horizontalLayoutContainer.add(html);
        BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 5, 0, 0));
        flex.setFlex(1);
        horizontalLayoutContainer.add(new Label(), flex);
        TextButton loadButton = new TextButton("Загрузить");
        loadButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (!imageSelector.validate()) return;
//                form.setAction("upload?imageId=" + imageSelector.getValue().id);
//                form.submit();
                submitBtn();
            }
        });
        horizontalLayoutContainer.add(loadButton, new BoxLayoutContainer.BoxLayoutData(new Margins(0,0,0,0)));
        imageContainer.add(horizontalLayoutContainer);


        image = new Image("/image?imageId=0");
        image.getElement().getStyle().setPosition(Style.Position.RELATIVE);


        imageContainer.add(image, new VerticalLayoutContainer.VerticalLayoutData());

        imagePanel.setWidget(imageContainer);
        container.setCenterWidget(imagePanel);
    }

    @Override
    public void show() {
        super.show();
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

    public static native void submitBtn()/*-{
        $wnd.document.getElementById('ImageUpload').submit();
    }-*/;
}
