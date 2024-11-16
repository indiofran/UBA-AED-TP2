package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;
import aed.data.TransportNode;
import aed.interfaces.PriorityQueueInterface;
import aed.utils.OldestComparator;
import aed.utils.ProfitableComparator;

public class BestEffort {
    private PriorityQueue<TransportNode> masRedituables;
    private PriorityQueue<TransportNode> masAntiguos;
    private CityStatistics estadisticas;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        TransportNode[] transportNodes = new TransportNode[traslados.length];
        for (int i = 0; i < traslados.length; i++) {
            Traslado t = traslados[i];
            transportNodes[i] = new TransportNode(t.id, t.origen, t.destino, t.gananciaNeta, t.timestamp);
        }

        estadisticas = new CityStatistics(cantCiudades);
        masRedituables = new PriorityQueue<>(transportNodes, new ProfitableComparator());
        masAntiguos = new PriorityQueue<>(transportNodes, new OldestComparator());

        ArrayList<PriorityQueueInterface.HandleInterface> handlesRedituable = masRedituables.getHandle();
        ArrayList<PriorityQueueInterface.HandleInterface> handlesAntiguos = masAntiguos.getHandle();
        for (int i = 0; i < transportNodes.length; i++) {
            TransportNode transportNode = transportNodes[i];
            PriorityQueueInterface.HandleInterface profitableHandle = handlesRedituable.get(i);
            PriorityQueueInterface.HandleInterface oldestHandle = handlesAntiguos.get(i);
            transportNode.updateProfitable(profitableHandle);
            transportNode.updateOldest(oldestHandle);
        }

    }

    public void registrarTraslados(Traslado[] traslados) {
        for(Traslado t : traslados){
            TransportNode traslado = new TransportNode(t.id, t.origen, t.destino, t.gananciaNeta, t.timestamp);
           PriorityQueue<TransportNode>.Handle hadleRedituable =  masRedituables.add(traslado);
           traslado.updateProfitable(hadleRedituable);
            PriorityQueue<TransportNode>.Handle hadleAntiguo = masAntiguos.add(traslado);
            traslado.updateOldest(hadleAntiguo);
        }
    }

    public int[] despacharMasRedituables(int n) {
        TransportNode[] traslados =  this.dispatch(n, masRedituables);
        int[] response = new int[traslados.length];
        for (int i = 0; i < traslados.length; i++ ) {
                masAntiguos.remove(traslados[i].getHandleOldest());
                response[i] = traslados[i].getId();
        }
        return response;
    }

    public int[] despacharMasAntiguos(int n) {
        TransportNode[] traslados =  this.dispatch(n, masAntiguos);
        int[] response = new int[traslados.length];
        for (int i = 0; i < traslados.length; i++ ) {
            masRedituables.remove(traslados[i].getHandleOldest());
            response[i] = traslados[i].getId();
        }
        return response;
    }

    private TransportNode[] dispatch(int n, PriorityQueue<TransportNode> pqueue) {
        int max = n < pqueue.size() ? n : pqueue.size();
        TransportNode[] traslados = new TransportNode[max];
        for (int i = 0; i < max; i++) {
            TransportNode t = pqueue.poll();
            traslados[i]= t;
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
        // Implementar
        return estadisticas.getAverageProfit();
    }

}
