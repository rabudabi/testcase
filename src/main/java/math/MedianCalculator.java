package math;

import com.vaadin.ui.TreeTable;

import java.util.*;

public class MedianCalculator {
    private TreeTable treeTable;
    private Map<Long, ArrayList<Double>> map = new HashMap<Long, ArrayList<Double>>();

    public MedianCalculator(TreeTable ttable){
        treeTable = ttable;
    }


    public void updateNodeMedianPrice(long id, double price){
        if(!(map.keySet().contains(id))){
            map.put(id, new ArrayList<Double>());
        }
        map.get(id).add(price);
        recalqulate(id);
    }


    @SuppressWarnings("unchecked")
    private void recalqulate(long id) {
        Collections.sort(map.get(id));
        ArrayList<Double> prom = map.get(id);
        double med;
        int len = prom.size();
        if(len%2 == 0){
            med = (prom.get(len/2) + prom.get(len/2 - 1))/2*len;
        } else {
            med = prom.get(len/2)*len;
        }
        treeTable.getItem(id).getItemProperty("Price").setValue(med);
    }
}
