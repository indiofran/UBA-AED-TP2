package aed;

import java.util.ArrayList;

import aed.data.ColaDePrioridad;
import aed.interfaces.ColaDePrioridadHandlesInterfaz;

public class EstadisticasDeCiudades {

    private ArrayList<Ciudades> ciudades;
    private ColaDePrioridadHandlesInterfaz<Ciudades> ciudadesConMayorSuperavit;
    private ArrayList<Integer> ciudadesConMayorGanancia;
    private ArrayList<Integer> ciudadesConMayorPerdida;
    private int gananciasTotales;
    private int trasladosDespachados;

    public EstadisticasDeCiudades(int cantCiudades) {
        //
    }

    public void actualizar(Traslado t) {
        // Actualizar estadisticas dado un traslado
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
