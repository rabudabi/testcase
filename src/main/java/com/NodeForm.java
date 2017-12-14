package com;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import dbService.DBException;
import dbService.DBNodeService;

class NodeForm {
    private String name;
    private DBNodeService dbNodeService;
    private TTable tTable;
    private ItemForm itemForm;

    NodeForm(TTable tTable, ItemForm itemForm){
        dbNodeService = new DBNodeService();
        this.itemForm = itemForm;
        this.tTable = tTable;
    }

    Component createInputNodes(){

        VerticalLayout vLot = new VerticalLayout();
        vLot.setSpacing(true);
        Label label1 = new Label("<h2 style=\"margin-block-end: auto\"> Добавление кассого узла: </h2>");
        label1.setContentMode(ContentMode.HTML);

        HorizontalLayout hLot = new HorizontalLayout();

        Label label2 = new Label("<b style=\"margin-right: 10px;\">Наименование:  </b>");
        label2.setContentMode(ContentMode.HTML);
        TextField casPoint = new TextField();
        casPoint.addTextChangeListener(textChangeEvent -> name = textChangeEvent.getText());

        hLot.addComponent(label2);
        hLot.addComponent(casPoint);
        hLot.setComponentAlignment(label2, Alignment.MIDDLE_CENTER);

        Button buttonAdd = new Button("Добавить");
        buttonAdd.addClickListener((Button.ClickEvent clickEvent) -> {
                try {
                    if((!(dbNodeService.chekNode(name)))&& (name != null)){
                        long id = dbNodeService.addNode(name);
                        tTable.addNode(name, id);
                        itemForm.updateNodeList(name);
                    } else {
                        Notification.show("This Node Already In DataBase",
                                name,
                                Notification.Type.ERROR_MESSAGE);
                        casPoint.clear();
                    }
                } catch (DBException e) {
                    e.printStackTrace();
                }

        });


        vLot.addComponent(label1);
        vLot.addComponent(hLot);
        vLot.addComponent(buttonAdd);
        vLot.setWidth("300px");

        return vLot;
    }
}
