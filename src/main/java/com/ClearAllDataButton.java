package com;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import dbService.DBException;
import dbService.DBItemService;
import dbService.DBNodeService;

class ClearAllDataButton {
    private TTable tTable;
    private ItemForm itemForm;

    ClearAllDataButton(TTable tTable, ItemForm itemForm){
        this.tTable = tTable;
        this.itemForm = itemForm;
    }

    Component addButton(){
        Button button = new Button("Clear all data");
        button.addClickListener(clickEvent ->{
            try {
                deleteDataFromDB();
                clearTable();
                itemForm.clearNodeList();
            } catch (DBException e) {
                e.printStackTrace();
            }
        } );
        return button;
    }

    private void deleteDataFromDB() throws DBException {
        new DBNodeService().clearAllNodes();
        new DBItemService().clearAllNodes();
    }

    private void clearTable() {
        tTable.clearTable();
    }
}
