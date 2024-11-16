package aed.utils;

import aed.Traslado;
import aed.data.City;

import java.util.Comparator;

public class CitySurplusComparator  implements Comparator<City> {

    @Override
    public int compare(City o1, City o2) {
        int surplus_o1 =  o1.getProfit() - o1.getLoses();
        int surplus_o2= o2.getProfit() - o2.getLoses();
        if (surplus_o1 != surplus_o2) {
            return Integer.compare(surplus_o1, surplus_o2);
        }
            return Integer.compare(o2.getId(), o1.getId());
    }
}
