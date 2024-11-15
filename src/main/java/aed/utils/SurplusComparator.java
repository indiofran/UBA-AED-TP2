package aed.utils;

import aed.Ciudad;

import java.util.Comparator;

public class SurplusComparator implements Comparator<Ciudad> {
    @Override
    public int compare(Ciudad c1, Ciudad c2) {
        int comparation = Integer.compare(c1.getSuperavit(), c2.getSuperavit());
        if (comparation == 0) {
            // Invierto el orden del compare ya que ahora es el id menor
            // Falta testear esto
            return Integer.compare(c2.getId(), c1.getId());
        }

        return comparation;
    }
}
