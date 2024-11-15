package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;
import aed.utils.OldestComparator;
import aed.utils.ProfitableComparator;

public class BestEffort {
    // Completar atributos privados
    private PriorityQueue<Traslado> masRedituables;
    private PriorityQueue<Traslado> masAntiguos;
    private CityStatistics estadisticas;

    public BestEffort(Ciudad[] ciudades, Traslado[] traslados) {
        estadisticas = new CityStatistics(ciudades);
        masAntiguos = new PriorityQueue<Traslado>(new OldestComparator());
        masRedituables = new PriorityQueue<Traslado>(new ProfitableComparator());
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
