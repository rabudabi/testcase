package com;

import com.vaadin.ui.TreeTable;
import dataSets.ItemDataSet;
import dataSets.NodeDataSet;
import dbService.DBException;
import dbService.DBItemService;
import dbService.DBNodeService;
import math.MedianCalculator;

import java.util.List;

class TTable {
    private DBItemService dbItemService = new DBItemService();
    private DBNodeService dbNodeService = new DBNodeService();
    private TreeTable ttable;
    private MedianCalculator medianCalculator;
    TTable() throws DBException {
        ttable = new TreeTable();
        ttable.addContainerProperty("Name", String.class, null);
        ttable.addContainerProperty("Price", Double.class, null);
        ttable.setWidth("500px");
        medianCalculator = new MedianCalculator(ttable);
        initTable();
    }

    TreeTable getTtable() {
        return ttable;
    }

    private void makeItShine() {
        for (Object itemId: ttable.getContainerDataSource()
                .getItemIds()) {
            ttable.setCollapsed(itemId, false);
        }
    }


    private void initTable() throws DBException {
        List<NodeDataSet> nodeDataSets = dbNodeService.getAllNodes();
        for (NodeDataSet nds: nodeDataSets) {
            addNode(nds.getName(), nds.getId());
            List<ItemDataSet> itemDataSets = dbItemService.getAllItemsForNode(nds.getId());
            for (ItemDataSet ids: itemDataSets) {
                addNodeItem(ids.getName(), ids.getPrice(), nds.getId(), ids.getId());
            }
        }

    }

    void addNode(String name, long id) {
        ttable.addItem(new Object[]{name, 0D}, id*10000);
        ttable.setChildrenAllowed(id*10000, false);
        makeItShine();
    }


    void addNodeItem(String name, double price, long node_id, long item_id){
        ttable.setChildrenAllowed(node_id*10000, true);
        ttable.addItem(new Object[]{name, price}, item_id);
        ttable.setParent(item_id, node_id*10000);
        ttable.setChildrenAllowed(item_id, false);
        makeItShine();
        medianCalculator.updateNodeMedianPrice(node_id*10000, price);
    }


    void clearTable(){
        ttable.removeAllItems();
    }
}
