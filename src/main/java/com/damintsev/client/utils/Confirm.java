package com.damintsev.client.utils;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebolgar at 27.03.13 12:21
 * Реализация Confirm
 */
class Confirm extends Window {

        private Runnable listener;

        public Confirm(String title, String[] messages) {
            super.setHeadingText(title);
            super.setModal(true);
            super.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.END);
            final TextButton yes = new TextButton("Продолжить");
            ContentPanel panel = new ContentPanel();
            panel.setHeaderVisible(false);
            StringBuilder markup = new StringBuilder();
            markup.append("<table width=100% cellpadding=0 cellspacing=5>");
            for (int i = 0; i < messages.length; i++) {
                markup.append("<tr><td style='vertical-align:top' class=confirm_box_" + i + "></td><td class=confirm_msg_" + i + "></td></tr>");
            }
            markup.append("</table>");
            final HtmlLayoutContainer body = new HtmlLayoutContainer(markup.toString());
            int i = 0;
            final List<CheckBox> list = new ArrayList<CheckBox>();
            ChangeHandler handler = new ChangeHandler() {
                public void onChange(ChangeEvent changeEvent) {
                    yes.setEnabled(false);
                    for (CheckBox box : list) {
                        if (!box.getValue()) return;
                    }
                    yes.setEnabled(true);
                }
            };
            for (String message : messages) {
                CheckBox box = new CheckBox();
                box.addChangeHandler(handler);
                list.add(box);
                body.add(box, new AbstractHtmlLayoutContainer.HtmlData(".confirm_box_" + i));
                Label label = new Label();
                label.getElement().setInnerHTML(message);
                body.add(label, new AbstractHtmlLayoutContainer.HtmlData(".confirm_msg_" + i));
                i++;
            }
            panel.setWidget(body);
            super.setWidget(panel);
            super.addButton(yes);
            yes.setEnabled(false);
            yes.addSelectHandler(new SelectEvent.SelectHandler() {
                public void onSelect(SelectEvent event) {
                    hide();
                    if (listener != null) listener.run();
                }
            });
            super.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
                public void onSelect(SelectEvent event) {
                    hide();
                }
            }));
        }

        public void show(Runnable listener) {
            this.listener = listener;
            super.show();
        }
    }

