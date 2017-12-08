package com;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import dbService.DBException;


public class MyVaadinApplication extends UI {

    private TTable tTable = new TTable();
    private ItemForm itemForm = new ItemForm(tTable);
    private NodeForm nodeForm = new NodeForm(tTable,  itemForm);
    private ClearAllDataButton clearAllDataButton = new ClearAllDataButton(tTable, itemForm);

    public MyVaadinApplication() throws DBException {
    }


    @Override
    public void init(VaadinRequest request) {

        HorizontalLayout layout = new HorizontalLayout();
        setContent(layout);
        setTheme("Light");
        try {
            layout.addComponent(createInputFilds());
            layout.addComponent(tTable.getTtable());
            layout.addComponent(clearAllDataButton.addButton());

        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private Component createInputFilds() throws DBException {
        VerticalLayout vlt = new VerticalLayout();
        vlt.addComponent(nodeForm.createInputNodes());
        vlt.addComponent(itemForm.createInputItems());
        return vlt;
    }
}
