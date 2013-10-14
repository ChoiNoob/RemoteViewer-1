package com.damintsev.client.v3.pages.windows;

import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.service.Service;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.damintsev.utils.Dialogs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeQueryEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;

/**
 * User: Damintsev Andrey
 * Date: 06.08.13
 * Time: 1:40
 */
public class AddTaskWindow implements Editor<Task>{

    private static AddTaskWindow instance;

    public static AddTaskWindow get() {
        if(instance == null) instance = new AddTaskWindow();
        return instance;
    }

    private TaskEditor driver = GWT.create(TaskEditor.class);
    private Window window;
    private TextButton delete;
    private Task task;
//    @Path("station")
    ComboBox<Station> station;
    SimpleComboBox<TaskType> type;
    TextField name;
    TextField command;
//    TextField queryBusy; // todo вернуть запрос на проверку
    @Ignore
    TextArea comment;

    private AddTaskWindow() {
        window = new Window();
        window.setModal(true);
        window.setPixelSize(350, 350);
        window.setHeadingText("Добавить новый транк");
        ContentPanel con = new ContentPanel();
        con.setHeaderVisible(false);
        con.setBodyStyle("padding: 5px");
        final VerticalLayoutContainer panel = new VerticalLayoutContainer();
        con.add(panel);
        
           ListStore<Station> store = new ListStore<Station>(new ModelKeyProvider<Station>() {
            public String getKey(Station item) {
                return "" + item.getId();
            }
        });

         station = new ComboBox<Station>(store, new LabelProvider<Station>() {
            public String getLabel(Station item) {
                return item.getName() == null ? "Адрес: " + item.getHost() : item.getName() + "(Адрес: " + item.getHost() + ")";
            }
        });
        station.setLoader(new PagingLoader<PagingLoadConfig, PagingLoadResult<Station>>(new DataProxy<PagingLoadConfig, PagingLoadResult<Station>>() {
            //            @Override
            public void load(PagingLoadConfig config, final com.google.gwt.core.client.Callback<PagingLoadResult<Station>, Throwable> callback) {
                System.out.println("load value");
                Service2.database.getStationList(new AsyncCallback<PagingLoadResult<Station>>() {
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    public void onSuccess(PagingLoadResult<Station> result) {
                        station.getStore().clear();
                        station.getStore().addAll(result.getData());
                    }
                });
            }
        }));
//        station.addSelectionHandler(new SelectionHandler<Station>() {
//            public void onSelection(SelectionEvent<Station> event) {
//                System.out.println("selecterd = " + event.getSelectedItem().getId());
//                station.setValue(event.getSelectedItem());
//            }
//        });
        station.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        station.setEditable(false);
        station.setAllowBlank(false);
        station.addBeforeQueryHandler(new BeforeQueryEvent.BeforeQueryHandler<Station>() {
            public void onBeforeQuery(BeforeQueryEvent<Station> stationBeforeQueryEvent) {
                station.getLoader().load();
            }
        });
        panel.add(new FieldLabel(station, "Станция"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        type = new SimpleComboBox<TaskType>(new LabelProvider<TaskType>() {
            public String getLabel(TaskType item) {
                return item.getName();
            }
        });
        type.add(TaskType.IP);
        type.add(TaskType.ISDN);
//        type.add(TaskType.PING);
        type.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        type.setEditable(false);
        type.setAllowBlank(false);
        panel.add(new FieldLabel(type, "Тип"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        name = new TextField();
        panel.add(new FieldLabel(name, "Имя"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        command = new TextField();
        command.setAllowBlank(false);
        panel.add(new FieldLabel(command, "Запрос состояния канала"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

//        queryBusy = new TextField();
//        panel.add(new FieldLabel(queryBusy, "Проверака занятых каналов"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        comment = new TextArea();
        comment.setHeight(70);
        panel.add(new FieldLabel(comment, "Комментарий"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        delete = new TextButton("Удалить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                Dialogs.confirm("Будет удалено утройство", new Runnable() {
                    public void run() {
                        Service2.database.deleteTask(task, new AsyncCallback<Void>() {
                            public void onFailure(Throwable caught) {
                                //To change body of implemented methods use File | Settings | File Templates.
                            }

                            public void onSuccess(Void result) {
                                MonitoringFrame.get().reloadView();
                            }
                        });
                        window.hide();
                    }
                });
            }
        });
        delete.hide();
        con.addButton(delete);

        con.addButton(new TextButton("Сохранить", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                task = driver.flush();
                if (driver.hasErrors()) return;
                System.out.println("st id=" + task.getStation());
                window.mask();
                Service2.database.saveTask(task, new AsyncCallback<Task>() {
                    public void onFailure(Throwable caught) {
                        Dialogs.alert("Cannot save device =" + caught.getMessage());
                    }

                    public void onSuccess(Task result) {
                        window.unmask();
                        window.hide();
//                        if (newEntity) MonitoringFrame.get().addItem(new UIItem(result));
                        MonitoringFrame.get().add(result);
                        window.hide();
                    }
                });
            }
        }));

        con.addButton(new TextButton("Отмена", new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent event) {
                window.hide();
            }
        }));
        window.setWidget(con);

    }

    public void show(Long id) {
        driver.initialize(this);
        window.show();
        if (id == null) {
            task = new Task();
            delete.hide();
            driver.edit(task);
        } else {
            delete.show();
            window.mask();
            Service2.database.loadTask(id, new AsyncCallback<Task>() {
                public void onFailure(Throwable caught) {
                    Dialogs.alert("Error loading device =" + caught.getMessage());
                }

                public void onSuccess(Task result) {
                    task = result;
                    window.unmask();
                    driver.edit(task);
                }
            });
        }
    }

    interface TaskEditor extends SimpleBeanEditorDriver<Task, AddTaskWindow> {

    }
//
////    @Path("station")
//    public ComboBox<Station> getStationComboBox() {
//        ListStore<Station> store = new ListStore<Station>(new ModelKeyProvider<Station>() {
//            public String getKey(Station item) {
//                return "" + item.getId();
//            }
//        });
//
//        final ComboBox<Station> station = new ComboBox<Station>(store, new LabelProvider<Station>() {
//            public String getLabel(Station item) {
//                return item.getName() == null ? "Адрес: " + item.getHost() : item.getName() + "(Адрес: " + item.getHost() + ")";
//            }
//        }){
//            @Override
//            public Station getValue() {
//                System.out.println("get VVV=" + super.getValue());
//                return super.getValue();    //To change body of overridden methods use File | Settings | File Templates.
//            }
//        };
//        station.setLoader(new PagingLoader<PagingLoadConfig, PagingLoadResult<Station>>(new DataProxy<PagingLoadConfig, PagingLoadResult<Station>>() {
////            @Override
//            public void load(PagingLoadConfig config, final com.google.gwt.core.client.Callback<PagingLoadResult<Station>, Throwable> callback) {
//                Service.instance.getStationList(new AsyncCallback<PagingLoadResult<Station>>() {
//                    public void onFailure(Throwable caught) {
//                        //To change body of implemented methods use File | Settings | File Templates.
//                    }
//
//                    public void onSuccess(PagingLoadResult<Station> result) {
//                        station.getStore().clear();
//                        station.getStore().addAll(result.getData());
//                    }
//                });
//            }
//        }));
//        station.addSelectionHandler(new SelectionHandler<Station>() {
//            public void onSelection(SelectionEvent<Station> event) {
//                System.out.println("selecterd = "+ event.getSelectedItem().getId());
//                station.setValue(event.getSelectedItem());
//            }
//        });
//        station.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
//
//        return station;
//    }
}
