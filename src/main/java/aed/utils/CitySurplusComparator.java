package aed.utils;

import aed.Traslado;
import aed.data.City;

import java.util.Comparator;

public class CitySurplusComparator  implements Comparator<City> {

    @Override
    public int compare(City o1, City o2) {
        int comparative = Integer.compare(o1.getSurplus(), o2.getSurplus());
        if (comparative == 0) {
            return Integer.compare(o2.getId(), o1.getId());
        }
        return comparative;
    }
}
