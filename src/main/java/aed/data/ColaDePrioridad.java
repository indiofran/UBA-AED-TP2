package aed.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import aed.interfaces.ColaDePrioridadInterfaz;

public class ColaDePrioridad<T> implements ColaDePrioridadInterfaz<T> {
    private ArrayList<T> elems;
    private Comparator<T> comp;

    public ColaDePrioridad(Comparator<T> comparator) {
        this.elems = new ArrayList<T>();
        this.comp = comparator;
    }

    public ColaDePrioridad(ArrayList<T> elems, Comparator<T> comparator) {
        this.elems = new ArrayList<T>(elems);
        this.comp = comparator;

        for (int i = elems.size() - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public void encolar(T elem) {
        elems.add(elem);
        siftUp(elems.size() - 1);
    }

    public T desencolar() {
        T elem = elems.get(0);
        elems.set(0, elems.get(elems.size() - 1));
        elems.remove(elems.size() - 1);
        siftDown(0);

        return elem;
    }

    public T obtenerMax() {
        return elems.get(0);
    }

    public int tamaño() {
        return elems.size();
    }

    public Iterator<T> iterator() {
        return elems.iterator();
    }

    private void swap(int i, int j) {
        T aux = elems.get(i);
        elems.set(i, elems.get(j));
        elems.set(j, aux);
    }

    private void siftUp(int i) {
        int padre = (i - 1) / 2;

        if (i != 0 && comp.compare(elems.get(i), elems.get(padre)) > 0) {
            swap(i, padre);
            siftUp(padre);
        }
    }

    private void siftDown(int i) {
        int izq = 2 * i + 1;
        int der = 2 * i + 2;

        if (der < elems.size()) {
            int max = comp.compare(elems.get(der), elems.get(izq)) > 0 ? der : izq;

            if (comp.compare(elems.get(max), elems.get(i)) > 0) {
                swap(i, max);
                siftDown(max);
            }
        } else if (izq < elems.size() && comp.compare(elems.get(izq), elems.get(i)) > 0) {
            swap(i, izq);
            siftDown(izq);
        }
    }
}