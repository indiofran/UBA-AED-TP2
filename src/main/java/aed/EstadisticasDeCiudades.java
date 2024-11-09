package aed;

import java.util.ArrayList;

import aed.data.ColaDePrioridad;

public class EstadisticasDeCiudades {

    private ArrayList<Ciudades> ciudades;
    private ColaDePrioridad<Ciudades> ciudadesConMayorSuperavit;
    private ArrayList<Integer> ciudadesConMayorGanancia;
    private ArrayList<Integer> ciudadesConMayorPerdida;
    private int gananciasTotales;
    private int trasladosDespachados;

    public EstadisticasDeCiudades(int cantCiudades) {
        //
    }

    public void actualizarGanancia(int ciudad, int ganancia) {
        gananciasTotales += ganancia;
        // ver como agregar a ciudades
    }

    public void actualizarPerdida(int ciudad, int perdida) {
        // Incrementar pérdida de una ciudad específica
    }

    public int obtenerSuperavit() {
        // Calcular superávit para una ciudad
        return 0;
    }

    public ArrayList<Integer> obtenerMayoresGanancias() {
        return ciudadesConMayorGanancia;
    }

    public ArrayList<Integer> obtenerMayoresPerdida() {
        return ciudadesConMayorPerdida;
    }

    public int obtenerGananciaPromedio() {
        return gananciasTotales / trasladosDespachados;
    }

}
