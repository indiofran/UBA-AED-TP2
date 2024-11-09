package aed;

import java.util.ArrayList;

import aed.data.ColaDePrioridad;

public class BestEffort {
    // Completar atributos privados
    private ColaDePrioridad<Traslado> masRedituables;
    private ColaDePrioridad<Traslado> masAntiguos;
    private EstadisticasDeCiudades estadisticas;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        estadisticas = new EstadisticasDeCiudades(cantCiudades);
        masAntiguos = new ColaDePrioridad<Traslado>(null);
        masRedituables = new ColaDePrioridad<Traslado>(null);
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
        return estadisticas.obtenerSuperavit();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return estadisticas.obtenerMayoresGanancias();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return estadisticas.obtenerMayoresPerdida();
    }

    public int gananciaPromedioPorTraslado() {
        // Implementar
        return estadisticas.obtenerGananciaPromedio();
    }

}
