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

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        estadisticas = new CityStatistics(cantCiudades);
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
        return estadisticas.getSurplus();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return estadisticas.getHigherProfits();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return estadisticas.getHigherLost();
    }

    public int gananciaPromedioPorTraslado() {
        // Implementar
        return estadisticas.getAverageProfit();
    }

}
