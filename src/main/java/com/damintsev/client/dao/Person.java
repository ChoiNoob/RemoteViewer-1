//package com.damintsev.client.dao;
//
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.editor.client.Editor;
//import com.google.gwt.editor.client.SimpleBeanEditorDriver;
//import com.google.gwt.user.client.ui.Label;
//import com.sencha.gxt.widget.core.client.Dialog;
//
///**
// * User: Damintsev Andrey
// * Date: 04.08.13
// * Time: 15:07
// */
//// Regular POJO, no special types needed
//public class Person {
////    Address getAddress();
////    Person getManager();
//    String name;
//    String getName() {
//        return name;
//    }
////    void setManager(Person manager);
//    void setName(String name){
//        this.name = name;
//    }
//}
//
//// Sub-editors are retrieved from package-protected fields, usually initialized with UiBinder.
//// Many Editors have no interesting logic in them
//class PersonEditor extends Dialog implements Editor<Person> {
//    // Many GWT Widgets are already compatible with the Editor framework
//    Label nameEditor;
//    // Building Editors is usually just composition work
////    AddressEditor addressEditor;
////    ManagerSelector managerEditor;
//
//    public PersonEditor() {
//        // Instantiate my widgets, usually through UiBinder
//    }
//}
//
//// A simple demonstration of the overall wiring
//class EditPersonWorkflow{
//    // Empty interface declaration, similar to UiBinder
//    interface Driver extends SimpleBeanEditorDriver<Person, PersonEditor> {}
//
//    // Create the Driver
//    Driver driver = GWT.create(Driver.class);
//
//    void edit(Person p) {
//        // PersonEditor is a DialogBox that extends Editor<Person>
//        PersonEditor editor = new PersonEditor();
//        // Initialize the driver with the top-level editor
//        driver.initialize(editor);
//        // Copy the data in the object into the UI
//        driver.edit(p);
//        // Put the UI on the screen.
//        editor.center();
//    }
//
//    // Called by some UI action
//    void save() {
//        Person edited = driver.flush();
//        if (driver.hasErrors()) {
//            // A sub-editor reported errors
//        }
//        doSomethingWithEditedPerson(edited);
//    }
//}
