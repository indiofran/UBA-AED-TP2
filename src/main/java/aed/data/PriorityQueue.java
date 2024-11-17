package aed.data;

import java.util.ArrayList;
import java.util.Comparator;

import aed.interfaces.PriorityQueueInterface;

public class PriorityQueue<T> implements PriorityQueueInterface<T> {
    private ArrayList<Node> nodes;
    private Comparator<T> cmp;

    public PriorityQueue(Comparator<T> comparator) {
        this.nodes = new ArrayList<Node>();
        this.cmp = comparator;
    }

    // Constructor de Priority Queue. El proceso de heapify o siftDown tiene una complejidad de O(log(n)),
// mientras que agregar elementos a una lista es O(1). Al agregar todos los elementos (n elementos),
// la complejidad total se calcula como O(n + log(n)). Dado que O(log(n)) es menor que O(n),
// la complejidad final se acota a O(n).
    public PriorityQueue(T[] elems, Comparator<T> comparator) {
        this.nodes = new ArrayList<Node>();
        this.cmp = comparator;

        for (int i = 0; i < elems.length; i++) {
            this.nodes.add(new Node(elems[i]));
        }

        for (int i = elems.length - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public Handle add(T elem) {
        Node newNode = new Node(elem);
        nodes.add(newNode);
        siftUp(nodes.size() - 1);

        return newNode.handle;
    }

    public T poll() {
        T elem = nodes.get(0).data;
        nodes.set(0, nodes.get(nodes.size() - 1));
        nodes.remove(nodes.size() - 1);
        siftDown(0);

        return elem;
    }

    public T peek() {
        return nodes.get(0).data;
    }

    public T remove(HandleInterface handle) {
        int i = handle.getIndex();
        if (i >= this.size()) {
            return null;
        }
        T elem = nodes.get(i).data;
        Node ultimo = nodes.get(nodes.size() - 1);

        nodes.remove(nodes.size() - 1);

        if (i >= nodes.size()) {
            return elem;
        }

        nodes.set(i, ultimo);
        ultimo.handle.refreshIndex(i);

        int padre = (i - 1) / 2;

        if (hasHigherPriority(i, padre)) {
            siftUp(i);
        } else {
            siftDown(i);
        }

        return elem;
    }

    public void refresh(HandleInterface handle, T elem) {
        int i = handle.getIndex();
        int padre = (i - 1) / 2;
        if (i < this.size()) {
            nodes.get(i).data = elem;

            if (hasHigherPriority(i, padre)) {
                siftUp(i);
            } else {
                siftDown(i);
            }
        }
    }

    public T get(HandleInterface handle) {
        return nodes.get(handle.getIndex()).data;
    }

    public ArrayList<HandleInterface> getHandle() {
        ArrayList<HandleInterface> listHandles = new ArrayList<HandleInterface>();

        for (Node node : nodes) {
            listHandles.add(node.handle);
        }

        return listHandles;
    }

    public int size() {
        return nodes.size();
    }

    public class Handle implements HandleInterface {
        private int indice;

        public Handle() {
            this.indice = nodes.size();
        }

        public int getIndex() {
            return indice;
        }

        public void refreshIndex(int i) {
            this.indice = i;
        }
    }

    private class Node {
        T data;
        Handle handle;

        public Node(T elem) {
            this.data = elem;
            this.handle = new Handle();
        }
    }

    private void swap(int i, int j) {
        nodes.get(i).handle.refreshIndex(j);
        nodes.get(j).handle.refreshIndex(i);

        Node aux = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, aux);
    }

    private void siftUp(int i) {
        int padre = (i - 1) / 2;

        if (i != 0 && hasHigherPriority(i, padre)) {
            swap(i, padre);
            siftUp(padre);
        }
    }
    // O(log n)
    private void siftDown(int i) {
        int izq = 2 * i + 1;
        int der = 2 * i + 2;

        if (der < nodes.size()) {
            int max = hasHigherPriority(der, izq) ? der : izq;

            if (hasHigherPriority(max, i)) {
                swap(i, max);
                siftDown(max);
            }
        } else if (izq < nodes.size() && hasHigherPriority(izq, i)) {
            swap(i, izq);
            siftDown(izq);
        }
    }

    private boolean hasHigherPriority(int i, int j) {
        return cmp.compare(nodes.get(i).data, nodes.get(j).data) > 0;
    }
}