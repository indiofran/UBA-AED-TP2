package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;
import aed.data.TransportNode;
import aed.interfaces.PriorityQueueInterface.HandleInterface;
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

        ArrayList<HandleInterface> handlesRedituable = masRedituables.getHandle();
        ArrayList<HandleInterface> handlesAntiguos = masAntiguos.getHandle();

        for (int i = 0; i < transportNodes.length; i++) {
            HandleInterface profitableHandle = handlesRedituable.get(i);
            HandleInterface oldestHandle = handlesAntiguos.get(i);
            masRedituables.get(profitableHandle).updateProfitable(profitableHandle);
            masAntiguos.get(oldestHandle).updateOldest(oldestHandle);
        }

    }

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado t : traslados) {
            TransportNode traslado = new TransportNode(t.id, t.origen, t.destino, t.gananciaNeta, t.timestamp);
            HandleInterface hadleRedituable = masRedituables.add(traslado);
            HandleInterface hadleAntiguo = masAntiguos.add(traslado);
            traslado.updateProfitable(hadleRedituable);
            traslado.updateOldest(hadleAntiguo);
        }
    }

    public int[] despacharMasRedituables(int n) {
        int min = n < masRedituables.size() ? n : masRedituables.size();
        int[] ids = new int[min];
        for (int i = 0; i < min; i++) {
            TransportNode t = masRedituables.poll();
            masAntiguos.remove(t.getHandleOldest());
            ids[i] = t.getId();
            estadisticas.refresh(t);
        }
        return ids;
    }

    public int[] despacharMasAntiguos(int n) {
        int min = n < masAntiguos.size() ? n : masAntiguos.size();
        int[] ids = new int[min];
        for (int i = 0; i < min; i++) {
            TransportNode t = masAntiguos.poll();
            masRedituables.remove(t.getHandleProfitable());
            ids[i] = t.getId();
            estadisticas.refresh(t);
        }
        return ids;
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
