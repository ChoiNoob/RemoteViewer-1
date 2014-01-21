/**
 * Created with IntelliJ IDEA.
 * User: adamintsev
 * Date: 21.01.14
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */

var win;
function openWindow() {

    var propertiesPanel = Ext.create('Ext.form.Panel', {
//        url:'save-form.php',
        frame:true,
        title: 'Simple Form with FieldSets',
        bodyStyle:'padding:5px 5px 0',
        width: 350,
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75
        },
        defaults: {
            anchor: '100%'
        },

        items: [{
            xtype:'fieldset',
            checkboxToggle:true,
            title: 'User Information',
            defaultType: 'textfield',
            collapsed: true,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items :[{
                fieldLabel: 'First Name',
                name: 'first',
                allowBlank:false
            },{
                fieldLabel: 'Last Name',
                name: 'last'
            },{
                fieldLabel: 'Company',
                name: 'company'
            }, {
                fieldLabel: 'Email',
                name: 'email',
                vtype:'email'
            }]
        },{
            xtype:'fieldset',
            title: 'Phone Number',
            collapsible: true,
            defaultType: 'textfield',
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items :[{
                fieldLabel: 'Home',
                name: 'home',
                value: '(888) 555-1212'
            },{
                fieldLabel: 'Business',
                name: 'business'
            },{
                fieldLabel: 'Mobile',
                name: 'mobile'
            },{
                fieldLabel: 'Fax',
                name: 'fax'
            }]
        }],

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
        }]
    });


    if (win == null) {
        win = Ext.create('widget.window', {
            title: 'Конфигурация изображений',
            closable: true,
            closeAction: 'hide',
//            animateTarget: this,
            width: 650,
            height: 550,
            layout: 'border',
            bodyStyle: 'padding: 5px;',
            items: [
                {
                    region: 'west',
//                    title: 'Navigation',
                    header: false,
                    width: 200,
                    split: true,
                    collapsible: true,
                    floatable: false,
                    items: propertiesPanel
                },
                {
                    region: 'center',
                    xtype: 'panel'
//                    items: [
//                        {
//                            title: 'Bogus Tab',
//                            html: 'Hello world 1'
//                        },
//                        {
//                            title: 'Another Tab',
//                            html: 'Hello world 2'
//                        },
//                        {
//                            title: 'Closable Tab',
//                            html: 'Hello world 3',
//                            closable: true
//                        }
//                    ]
                }
            ]
        });
    }
    win.show();
}