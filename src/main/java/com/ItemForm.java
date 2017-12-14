package com;

import com.vaadin.data.util.ListSet;
import com.vaadin.event.FieldEvents;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import dataSets.NodeDataSet;
import dbService.DBException;
import dbService.DBItemService;
import dbService.DBNodeService;

import java.util.List;

class ItemForm {
    private DBItemService dbItemService;
    private DBNodeService dbNodeService;
    private NativeSelect ns;
    private String name;
    private String node_name;
    private double price;
    private TTable tTable;
    private TextField textField1 = new TextField();
    private TextField textField2 = new TextField();


    ItemForm(TTable tTable){
        dbItemService = new DBItemService();
        dbNodeService = new DBNodeService();
        this.tTable = tTable;
    }


    Component createInputItems() throws DBException {
        VerticalLayout vlt = new VerticalLayout();
        //ЗАголовок
        Label label = new Label("<h2 style=\"margin-block-end: auto\"> Добавление банкомата: </h2>");
        label.setContentMode(ContentMode.HTML);
        vlt.addComponent(label);
        //Наименование
        vlt.addComponent(addInputField("<b style=\"margin-right: 10px;\">Наименование:  </b>", textField1,
                textChangeEvent -> name = textChangeEvent.getText()));
        //Стоимость инкасации
        vlt.addComponent(addInputField("<b style=\"margin-right: 10px;\">Стоимость инкасации (руб.):  </b>", textField2,
                textChangeEvent -> price = Double.parseDouble(textChangeEvent.getText())));

        //выбор узла
        HorizontalLayout hlt3 = new HorizontalLayout();
        Label label2 = new Label("<b style=\"margin-right: 10px;\">Выбор узла:  </b>");
        label2.setContentMode(ContentMode.HTML);
        ns = new NativeSelect(null, addEnableNodes());
        ns.addValueChangeListener(valueChangeEvent -> node_name = (String) valueChangeEvent.getProperty().getValue());

        hlt3.addComponent(label2);
        hlt3.addComponent(ns);
        hlt3.setComponentAlignment(ns, Alignment.MIDDLE_CENTER);
        hlt3.setComponentAlignment(label2, Alignment.MIDDLE_CENTER);
        //кнопка
        Button but = new Button("Добавить");
        but.addClickListener(clickEvent -> {
            if((name != null) && (price >= 0) && (ns.size() > 0)){
                try {
                    if(!dbItemService.chekItem(name)){
                        long id = dbItemService.addItem(name, price, dbNodeService.getNode(node_name).getId());

                        tTable.addNodeItem(name, price, dbNodeService.getNode(node_name).getId(), id);
                    } else {
                        Notification.show("This Item Already In DataBase",
                                name,
                                Notification.Type.ERROR_MESSAGE);
                        textField1.clear();
                        textField2.clear();
                    }

                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        });



        vlt.addComponent(hlt3);
        vlt.addComponent(but);
        return vlt;
    }

    private Component addInputField(String str, TextField textField, FieldEvents.TextChangeListener itemListener){
        HorizontalLayout hlt = new HorizontalLayout();
        Label label = new Label(str);
        label.setContentMode(ContentMode.HTML);
        textField.addTextChangeListener(itemListener);
        hlt.addComponent(label);
        hlt.addComponent(textField);
        hlt.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        return hlt;
    }

    private List<String> addEnableNodes() throws DBException {
            List<NodeDataSet> lll = dbNodeService.getAllNodes();
            List<String> nodes_name = new ListSet<>();
        for (NodeDataSet i :
                lll) {
            nodes_name.add(i.getName());
        }

        return nodes_name;
    }
    void clearNodeList(){
        ns.removeAllItems();
        ns.clear();
    }


    void updateNodeList(String name) {
        ns.addItem(name);
    }
}
