package aed.utils;

import aed.Traslado;
import aed.data.TransportNode;

import java.util.Comparator;

public class ProfitableComparator implements Comparator<TransportNode> {

    @Override
    public int compare(TransportNode t1, TransportNode t2) {
        if (t1.getProfit() != t2.getProfit()) {
            return Integer.compare(t1.getProfit(), t2.getProfit());
        }
        return Integer.compare(t1.getId(), t2.getId());
    }
}

