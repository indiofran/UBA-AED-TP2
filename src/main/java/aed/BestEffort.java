package aed;

import java.util.ArrayList;
import java.util.Collections;

import aed.data.PriorityQueue;
import aed.data.TransportNode;
import aed.interfaces.PriorityQueueInterface;
import aed.utils.OldestComparator;
import aed.utils.ProfitableComparator;

public class BestEffort {
    // Completar atributos privados
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
        // Implementar
    }

    public int[] despacharMasRedituables(int n) {
        // masRedituable.first()
        return null;
    }

    public int[] despacharMasAntiguos(int n) {
        // masAntiguos.first()
        return null;
    }

    public int ciudadConMayorSuperavit() {
        // Implementar
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
