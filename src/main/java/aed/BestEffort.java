package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;
import aed.data.TransportNode;
import aed.interfaces.PriorityQueueInterface;
import aed.utils.OldestComparator;
import aed.utils.ProfitableComparator;

public class BestEffort {
    private PriorityQueue<Traslado> masRedituables;
    private PriorityQueue<Traslado> masAntiguos;
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
           PriorityQueue<Traslado>.Handle hadleRedituable =  masRedituables.add(traslado);
           traslado.updateProfitable(hadleRedituable);
            PriorityQueue<Traslado>.Handle hadleAntiguo = masAntiguos.add(traslado);
            traslado.updateOldest(hadleAntiguo);
        }
    }

    public int[] despacharMasRedituables(int n) {
        return dispatch(n, masRedituables);
    }

    public int[] despacharMasAntiguos(int n) {
        return dispatch(n, masAntiguos);
    }

    private int[] dispatch(int n, PriorityQueue<Traslado> pqueue) {
        int max = n < pqueue.size() ? n : pqueue.size();
        int[] traslados = new int[max];
        for (int i = 0; i < max; i++) {
            Traslado t = pqueue.poll();
            traslados[i]= t.getId();
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
