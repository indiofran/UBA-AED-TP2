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

    // Constructor de Priority Queue. Primero itera todos los elementos de elems y
    // crea un nodo en O(1) por cada uno de ellos, por lo que el primer bucle tiene
    // complejidad O(n). Luego se realiza el algortimo de Floyd para la construcción
    // del heap a partir del array elems, lo cual es O(n) ya que si bien la
    // operación de siftDown es O(log n) en peor caso, no todos los nodos requieren
    // recorrer toda la altura del heap. Los nodos más cercanos a las hojas son
    // muchos pero requieren menos operaciones, mientras que los nodos cercanos a la
    // raíz son pocos pero requieren mas operaciones. Gracias a esta distribución de
    // las operaciones, la complejidad del algortimo de Floyd es O(n) en vez de
    // O(nlog n), y por lo tanto la complejidad del constructor es también O(n).
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

    // El método add crea un nodo en O(1) y lo añade al final del array, que es
    // O(1), luego realiza la operación de siftUp desde el último nivel, lo cual
    // tiene una complejidad de O(log n) siendo n la cantidad de elementos del
    // array. Por lo que la complejidad del método es O(log n).
    public Handle add(T elem) {
        Node newNode = new Node(elem);
        nodes.add(newNode);
        siftUp(nodes.size() - 1);

        return newNode.handle;
    }

    // El método poll obtiene y modifica una posición de un array, lo cual es O(1)
    // ámbas. Luego elimina el último elemento del array, que también es O(1), y por
    // último realiza la operación de siftDown desde la raíz, lo cual es O(log n).
    // Por lo que la complejidad del método es O(log n)
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

    // El método remove realiza varias operaciones de acceder y modificar una
    // posición de un array y eliminar el último elemento de un array, lo cual son
    // todas O(1). Por último realiza una operación de siftUp o
    // siftDown dependiendo de la dirección en la cual se debe corregir el heap, y
    // ambas en peor caso son O(log n) De esta manera la complejidad del método es
    // O(log n).
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

    // El método refresh obtiene y modifica una posición de un array en O(1). Luego,
    // al igual que el método remove realiza la operación de siftUp o siftDown
    // dependiendo de la situación, y ambás son O(log n) en peor caso.
    // Por lo que la complejidad final es O(log n).
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

    // El método getHandle recorre toda la priorityQueue (O(n)) y añade el handle de
    // cada nodo al array O(1), por lo que la complejidad es O(n).
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

    // El método siftUp es un método recursivo que dada un posición del heap,
    // compara y swapea (ambas O(1)) con su padre de manera recursiva hasta la raíz
    // en caso de ser
    // necesario. En el peor caso se arranca desde el útlimo nivel del heap y se
    // tiene que recorrer toda la altura del heap. Como un heap es un árbol binario
    // perfectamente balanceado, tiene altura log(n), por lo que la complejidad del
    // método es O(log n).
    private void siftUp(int i) {
        int padre = (i - 1) / 2;

        if (i != 0 && hasHigherPriority(i, padre)) {
            swap(i, padre);
            siftUp(padre);
        }
    }

    // El método siftDown es similar al método siftUp con la diferencia de que la
    // comparación y el swapeo lo hace con el mayor de sus hijos, y el peor caso es
    // cuando se inicia desde la raíz hasta el último nivel en caso de ser
    // necesario. Por lo tanto la complejidad es también O(log n).
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