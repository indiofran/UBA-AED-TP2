package aed;

import aed.data.PriorityQueue;
import aed.data.TransportNode;
import aed.interfaces.PriorityQueueInterface.HandleInterface;
import aed.utils.OldestComparator;
import aed.utils.ProfitableComparator;

import java.util.ArrayList;

public class BestEffort {
    private PriorityQueue<TransportNode> masRedituables;
    private PriorityQueue<TransportNode> masAntiguos;
    private CityStatistics estadisticas;

// En este caso, el constructor realiza varias operaciones secuenciales, donde t representa la cantidad de traslados
// y c la cantidad de ciudades. Primero, hay un bucle para crear TransportNode, con una complejidad de O(t).
// Luego, inicializamos cityStatics, lo que tiene una complejidad de O(c).
// A continuación, se inicializan secuencialmente dos PriorityQueue, cada una con complejidad O(t), lo que equivale a O(2t) = O(t).
// Posteriormente, se ejecuta getHandle con una complejidad de O(t). Finalmente, hay un último bucle con complejidad O(t)
// para completar los respectivos handles. Al sumar todas estas complejidades, obtenemos un total de O(t + c + t + t + t),
// lo que equivale a O(4t + c), y se acota como O(t + c).

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        TransportNode[] transportNodes = new TransportNode[traslados.length];
        for (int i = 0; i < traslados.length; i++) {
            Traslado t = traslados[i];
            transportNodes[i] = new TransportNode(t.id, t.origen, t.destino, t.gananciaNeta, t.timestamp);
        }

        estadisticas = new CityStatistics(cantCiudades);
        masRedituables = new PriorityQueue<>(transportNodes, new ProfitableComparator());
        masAntiguos = new PriorityQueue<>(transportNodes, new OldestComparator());

        ArrayList<HandleInterface> handlesRedituable = masRedituables.getHandle();
        ArrayList<HandleInterface> handlesAntiguos = masAntiguos.getHandle();

        for (int i = 0; i < transportNodes.length; i++) {
            HandleInterface profitableHandle = handlesRedituable.get(i);
            HandleInterface oldestHandle = handlesAntiguos.get(i);
            masRedituables.get(profitableHandle).updateProfitable(profitableHandle);
            masAntiguos.get(oldestHandle).updateOldest(oldestHandle);
        }

    }

// En el metodo registrar traslado, se utiliza un bucle que itera t veces, donde t representa la cantidad de traslados.
// Dentro de este bucle se realizan varias operaciones. Una de estas es add, que se ejecuta de forma secuencial
// y tiene una complejidad de O(log(n)), lo que da un total de 2log(n), que se acota a O(log(n)).
// También se realizan operaciones de update, ambas con una complejidad de O(1).
// Dado que las operaciones O(log(n)) se realizan t veces, la complejidad total es O(t * log(n)).

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado t : traslados) {
            TransportNode traslado = new TransportNode(t.id, t.origen, t.destino, t.gananciaNeta, t.timestamp);
            HandleInterface hadleRedituable = masRedituables.add(traslado);
            HandleInterface hadleAntiguo = masAntiguos.add(traslado);
            traslado.updateProfitable(hadleRedituable);
            traslado.updateOldest(hadleAntiguo);
        }
    }

    // El metodo despacharMasRedituables realiza varias operaciones secuenciales.
// Primero, llama a dispatch, cuya complejidad es O(n * (log(t) + log(c))), siendo n el número de traslados a despachar
// y t la cantidad de traslado en las colas y c la cantidad de ciudades. Luego, recorre los traslados con un bucle de O(n),
// donde en cada iteración realiza una operación de remove (O(log(n))).
// La complejidad total es O(n * (log(t) + log(c))) + O(n * log(n)). siendo entonces n*log(n) + n*log(c) + n*log(n) que se puede acotar por
//    n*log(n) + n*log(c) que es lo mismo que n*(log(n) + log(c))


    public int[] despacharMasRedituables(int n) {
        TransportNode[] traslados = this.dispatch(n, masRedituables);
        int[] response = new int[traslados.length];

        for (int i = 0; i < traslados.length; i++) {
            masAntiguos.remove(traslados[i].getHandleOldest());
            response[i] = traslados[i].getId();
        }

        return response;
    }

//    homologo a despachar mas redituable
    public int[] despacharMasAntiguos(int n) {
        TransportNode[] traslados = this.dispatch(n, masAntiguos);
        int[] response = new int[traslados.length];

        for (int i = 0; i < traslados.length; i++) {
            masRedituables.remove(traslados[i].getHandleProfitable());
            response[i] = traslados[i].getId();
        }

        return response;
    }
// El metodo dispatch comienza determinando el mínimo entre n y el tamaño de la PriorityQueue,
// lo cual tiene una complejidad de O(1). Luego, itera min veces,
// donde en cada iteración realiza una operación de poll O(log(t)
// y llama a estadisticas.refresh   O(log(t) + log(c) ), donde c es la cantidad de ciudades.
// Por lo tanto, la complejidad total del metodo es O(n * (log(t) + log(c))
// donde n es el número de traslados a despachar, t es la cantidad de traslados.


    private TransportNode[] dispatch(int n, PriorityQueue<TransportNode> pqueue) {
        int min = n < pqueue.size() ? n : pqueue.size();
        TransportNode[] traslados = new TransportNode[min];

        for (int i = 0; i < min; i++) {
            TransportNode t = pqueue.poll();
            traslados[i] = t;
            estadisticas.refresh(t);
        }

        return traslados;
    }

    public int ciudadConMayorSuperavit() {
        return estadisticas.getHighestSurplus();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return estadisticas.getHighestProfits();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return estadisticas.getHighestLost();
    }

    public int gananciaPromedioPorTraslado() {
        return estadisticas.getAverageProfit();
    }

}
