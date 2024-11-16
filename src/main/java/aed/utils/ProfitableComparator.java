package aed.utils;

import aed.Traslado;

import java.util.Comparator;

public class ProfitableComparator implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        if (t1.getProfit() != t2.getProfit()) {
            return Integer.compare(t2.getProfit(), t1.getProfit());
        }
        return Integer.compare(t1.getId(), t2.getId());
    }
}

