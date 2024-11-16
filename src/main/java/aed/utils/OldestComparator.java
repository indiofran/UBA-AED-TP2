package aed.utils;

import aed.Traslado;
import aed.data.TransportNode;

import java.util.Comparator;

public class OldestComparator implements Comparator<TransportNode> {

    @Override
    public int compare(TransportNode t1, TransportNode t2) {
        // Comparar traslados según rentabilidad o antigüedad
        return Integer.compare(t2.getTimestamp(), t1.getTimestamp());
    }
}
