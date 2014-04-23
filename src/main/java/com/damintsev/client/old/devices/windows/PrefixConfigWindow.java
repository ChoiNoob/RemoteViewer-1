//package com.damintsev.client.old.devices.windows;
//
//import com.damintsev.common.utils.Dialogs;
//import com.sencha.gxt.widget.core.client.ContentPanel;
//import com.sencha.gxt.widget.core.client.Window;
//import com.sencha.gxt.widget.core.client.button.TextButton;
//import com.sencha.gxt.widget.core.client.event.SelectEvent;
//import com.sencha.gxt.widget.core.client.form.TextArea;
//
//import java.util.TreeMap;
//
///**
// * User: Damintsev Andrey
// * Date: 24.08.13
// * Time: 2:15
// */
//public class PrefixConfigWindow {
//
//    private static PrefixConfigWindow instance;
//
//    public static PrefixConfigWindow get() {
//        if(instance == null) instance = new PrefixConfigWindow();
//        return instance;
//    }
//
//    private Window window;
//    private TextArea textArea;
//
//    private PrefixConfigWindow(){
//        window = new Window();
//        window.setPixelSize(300,300);
//        window.setModal(true);
//        window.setHeadingText("Настройка префиксов номеров телефонов");
//
//        ContentPanel panel = new ContentPanel();
//        panel.setHeaderVisible(true);
//        panel.setHeadingText("Номер:имя; (7921:МегаФон;)");
//        textArea = new TextArea();
//        panel.add(textArea);
//        panel.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
//            public void onSelect(SelectEvent event) {
//                TreeMap<String, String> map = new TreeMap<String, String>();
//                String text = textArea.getText();
//                text = text.replace("\n","");
//                System.out.println("Text=" + text);
//                String[] strings = text.split(";");
//                if(text.equals("")) {
//                    save(map);
//                }else
//                if (strings.length > 0) {
//                    for (String keyValue : strings) {
//                        String[] kv = keyValue.split(":");
//                        if (kv.length != 2) {
//                            Dialogs.alert("Ошибка задания таблица префиксов");
//                            return;
//                        }
//                        map.put(kv[0], kv[1]);
//                    }
//                    save(map);
//                }
//            }
//        }));
//        panel.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
//            public void onSelect(SelectEvent event) {
//                window.hide();
//            }
//        }));
//        window.setWidget(panel);
//    }
//
//    public void show() {
//        window.show();
////        Service.instance.loadPrefix(new AsyncCallback<TreeMap<String, String>>() {
////            public void onFailure(Throwable caught) {
////                Dialogs.alert("Failed to load prefix map=" + caught.getMessage());
////            }
////
////            public void onSuccess(TreeMap<String, String> result) {
////                textArea.clear();
////                StringBuilder sb = new StringBuilder(result.size() * 10);
////                for(Map.Entry<String, String> entry : result.entrySet()) {
////                    sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";\n");
////                }
////                textArea.setText(sb.toString());
////            }
////        });
//    }
//
//    private void save(TreeMap<String, String> map) {
////        Service.instance.savePrefix(map, new AsyncCallback<Void>() {
////            public void onFailure(Throwable caught) {
////                Dialogs.alert("Problem with saving =" + caught.getMessage());
////            }
////
////            public void onSuccess(Void result) {
////                window.hide();
////            }
////        });
//    }
//}
