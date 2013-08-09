package com.damintsev.utils;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Created by ebolgar at 23.01.13 11:20
 * Диалоги
 */
public class Dialogs {

        public static void confirm(String message, Runnable listener) {
            confirm("Подтверждение", message, listener);
        }

        public static void confirm(String message, Runnable access, Runnable cancel) {
            confirm("Подтверждение", message, access, cancel);
        }

        public static void confirm(String title, String message, final Runnable listener) {
            confirm(title, message, listener, null);
        }

        public static void confirm(String title, String message, final Runnable access, final Runnable cancel) {
            ConfirmMessageBox box = new ConfirmMessageBox(title, message);
            box.setPredefinedButtons(Dialog.PredefinedButton.YES, Dialog.PredefinedButton.NO);
            box.getButtonById(Dialog.PredefinedButton.YES.name()).addSelectHandler(new SelectEvent.SelectHandler() {
//                @Override
                public void onSelect(SelectEvent event) {
                    access.run();
                }
            });
            box.getButtonById(Dialog.PredefinedButton.NO.name()).addSelectHandler(new SelectEvent.SelectHandler() {
//                @Override
                public void onSelect(SelectEvent event) {
                    if (cancel != null) cancel.run();
                }
            });
            box.show();
        }

        public static void confirm(String[] messages, final Runnable listener) {
            if (messages != null) confirm("Подтверждение", messages, listener);
            else listener.run();
        }

        /**
         * Вызов confirm с множественными сообщениями
         *
         * @param title    заголовок
         * @param messages сообщения в html формате
         * @param listener обработчик в случае выбора
         */
        public static void confirm(String title, String[] messages, final Runnable listener) {
            new Confirm(title, messages).show(listener);
        }

        /**
         * Информационное сообщение
         *
         * @param message сообщение
         */
        public static void message(String message) {
            alert(MessageBox.ICONS.info(), "Сообщение", message, null);
        }

        /**
         * Информационное сообщение
         *
         * @param message  сообщение
         * @param listener действие при закрытии
         */
        public static void message(String message, final Runnable listener) {
            alert(MessageBox.ICONS.info(), "Сообщение", message, listener);
        }

        /**
         * Обработка сообщения о незначемой ошибке при ajax запросе
         *
         * @param message сообщение
         */
        public static void error(String message) {
            Info.display("Ошибка", message);
        }

        /**
         * Сообщение об ошибке в виде alert
         *
         * @param message сообщение
         */
        public static void alert(String message) {
            alert("Ошибка", message);
        }

        public static void alert(String message, Runnable listener) {
            alert("Ошибка", message, listener);
        }

        public static void alert(String title, String message) {
            alert(title, message, null);
        }

        public static void alert(String title, String message, final Runnable listener) {
            alert(null, title, message, listener);
        }

        public static void alert(ImageResource icon, String title, String message, final Runnable listener) {
            AlertMessageBox box = new AlertMessageBox(title, message) {
                @Override
                protected void onKeyPress(Event we) {
                    super.onKeyPress(we);
                    if (we.getKeyCode() == 13) hide();
                }

                @Override
                protected void onHide() {
                    super.onHide();
                    if (listener != null) listener.run();
                }
            };
            if (icon != null) box.setIcon(icon);
            box.show();
        }
    }

