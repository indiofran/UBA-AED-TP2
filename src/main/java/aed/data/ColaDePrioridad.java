package aed.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import aed.interfaces.ColaDePrioridadInterfaz;

public class ColaDePrioridad<T> implements ColaDePrioridadInterfaz<T> {
    private ArrayList<Nodo> nodos;
    private Comparator<T> cmp;

    public ColaDePrioridad(Comparator<T> comparator) {
        this.nodos = new ArrayList<Nodo>();
        this.cmp = comparator;
    }

    public ColaDePrioridad(T[] elems, Comparator<T> comparator) {
        this.nodos = new ArrayList<Nodo>();
        this.cmp = comparator;

        for (int i = 0; i < elems.length; i++) {
            this.nodos.add(new Nodo(elems[i]));
        }

        for (int i = elems.length - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public Handle encolar(T elem) {
        Nodo nuevoNodo = new Nodo(elem);
        nodos.add(nuevoNodo);
        siftUp(nodos.size() - 1);

        return nuevoNodo.handle;
    }

    public T desencolar() {
        T elem = nodos.get(0).data;
        nodos.set(0, nodos.get(nodos.size() - 1));
        nodos.remove(nodos.size() - 1);
        siftDown(0);

        return elem;
    }

    public T obtenerMax() {
        return nodos.get(0).data;
    }

    public T eliminar(HandleInterface handle) {
        int i = handle.obtenerIndice();
        T elem = nodos.get(i).data;
        Nodo ultimo = nodos.get(nodos.size() - 1);

        nodos.remove(nodos.size() - 1);

        if (i >= nodos.size()) {
            return elem;
        }

        nodos.set(i, ultimo);
        ultimo.handle.actualizarIndice(i);

        int padre = (i - 1) / 2;

        if (tieneMayorPrioridad(i, padre)) {
            siftUp(i);
        } else {
            siftDown(i);
        }

        return elem;
    }

    public void actualizar(HandleInterface handle, T elem) {
        int i = handle.obtenerIndice();
        int padre = (i - 1) / 2;

        nodos.get(i).data = elem;

        if (tieneMayorPrioridad(i, padre)) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    public T obtener(HandleInterface handle) {
        return nodos.get(handle.obtenerIndice()).data;
    }

    public ArrayList<HandleInterface> obtenerHandles() {
        ArrayList<HandleInterface> listaHandles = new ArrayList<HandleInterface>();

        for (Nodo nodo : nodos) {
            listaHandles.add(nodo.handle);
        }

        return listaHandles;
    }

    public int tamaño() {
        return nodos.size();
    }

    public class Handle implements HandleInterface {
        private int indice;

        public Handle() {
            this.indice = nodos.size();
        }

        public int obtenerIndice() {
            return indice;
        }

        public void actualizarIndice(int i) {
            this.indice = i;
        }
    }

    private class Nodo {
        T data;
        Handle handle;

        public Nodo(T elem) {
            this.data = elem;
            this.handle = new Handle();
        }
    }

    private void swap(int i, int j) {
        nodos.get(i).handle.actualizarIndice(j);
        nodos.get(j).handle.actualizarIndice(i);

        Nodo aux = nodos.get(i);
        nodos.set(i, nodos.get(j));
        nodos.set(j, aux);
    }

    private void siftUp(int i) {
        int padre = (i - 1) / 2;

        if (i != 0 && tieneMayorPrioridad(i, padre)) {
            swap(i, padre);
            siftUp(padre);
        }
    }

    private void siftDown(int i) {
        int izq = 2 * i + 1;
        int der = 2 * i + 2;

        if (der < nodos.size()) {
            int max = tieneMayorPrioridad(der, izq) ? der : izq;

            if (tieneMayorPrioridad(max, i)) {
                swap(i, max);
                siftDown(max);
            }
        } else if (izq < nodos.size() && tieneMayorPrioridad(izq, i)) {
            swap(i, izq);
            siftDown(izq);
        }
    }

    private boolean tieneMayorPrioridad(int i, int j) {
        return cmp.compare(nodos.get(i).data, nodos.get(j).data) > 0;
    }
}