/**
 * Created with IntelliJ IDEA.
 * User: adamintsev
 * Date: 21.01.14
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */

var win;
function openWindow() {

    // The data store containing the list of states
    var states = Ext.create('Ext.data.Store', {
        fields: ['abbr', 'name'],
        data : [
            {"abbr":"AL", "name":"Alabama"},
            {"abbr":"AK", "name":"Alaska"},
            {"abbr":"AZ", "name":"Arizona"}
        ]
    });

// Create the combo box, attached to the states data store
    var comboBox = Ext.create('Ext.form.ComboBox', {
        fieldLabel: 'Choose State',
        store: states,
        queryMode: 'local',
        displayField: 'name',
        valueField: 'abbr'
    });

    var propertiesPanel = Ext.create('Ext.form.Panel', {
//        url:'save-form.php',
        frame: true,
        title: false,
        header: false,
        bodyStyle: 'padding:5px 5px 0',
        width: 200,
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 50
        },
        defaults: {
            anchor: '100%'
        },

        items: [
            comboBox,
            {
                xtype: 'textfield',
                fieldLabel: 'Test epta'
            }



//            xtype:'',
////            title: 'Phone Number',
//            collapsible: true,
//            defaultType: 'textfield',
//            layout: 'anchor',
//            defaults: {
//                anchor: '100%'
//            },
//            items :[{
//                fieldLabel: 'Home',
//                name: 'home',
//                value: '(888) 555-1212'
//            },{
////                fieldLabel: 'Business',
//                name: 'business'
//            },{
////                fieldLabel: 'Mobile',
//                name: 'mobile'
//            },{
////                fieldLab/el: 'Fax',
//                name: 'fax'
//            }]
        ],

        buttons: [
            {
                text: 'Save'
            },
            {
                text: 'Cancel'
            }
        ]
    });


    if (win == null) {
        win = Ext.create('widget.window', {
            title: 'Конфигурация изображений',
            closable: true,
            closeAction: 'hide',
            width: 650,
            height: 550,
            layout: 'border',
            bodyStyle: 'padding: 5px;',
            items: [
                {
                    region: 'west',
                    title: false,
                    width: 200,
                    split: true,
                    collapsible: true,
                    floatable: false,
                    items: propertiesPanel
                },
                {
                    region: 'center',
                    xtype: 'panel'

                }
            ]
        });
    }
    win.show();
}