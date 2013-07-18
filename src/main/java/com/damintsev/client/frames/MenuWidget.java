package com.damintsev.client.frames;

import com.damintsev.client.model.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 18.07.13
 * Time: 22:07
 */
public class MenuWidget {

    public static Widget createMenu() {

        TreeStore<MenuItem> store = new TreeStore<MenuItem>(new ModelKeyProvider<MenuItem>() {
            public String getKey(MenuItem item) {
                return item.getName();
            }
        });

        Tree<MenuItem, String> tree = new Tree<MenuItem, String>(store, new ValueProvider<MenuItem, String>() {
            public String getValue(MenuItem object) {
                return object.getName();
            }

            public void setValue(MenuItem object, String value) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getPath() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }){
            @Override
            protected boolean hasChildren(MenuItem model) {
                return true;
            }
        };

        List<MenuItem> items = new ArrayList<MenuItem>();
        for(int i = 0; i < 10; i++) {
            MenuItem item = new MenuItem();
            item.setName("test " + i);
            items.add(item);
        }

        tree.getStore().add(items);


        return tree;
    }

}
