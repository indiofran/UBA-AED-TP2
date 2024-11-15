package aed.utils;

import aed.Traslado;

import java.util.Comparator;

public class OldestComparator implements Comparator<Traslado> {

    @Override
    public int compare(Traslado t1, Traslado t2) {
        // Comparar traslados según rentabilidad o antigüedad
        return Integer.compare(t1.getTimestamp(), t2.getTimestamp());
    }
}
