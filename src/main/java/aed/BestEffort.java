package aed;

import aed.data.Heap;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    private Heap masRedituables;
    private Heap masAntiguos;
    private EstadisticasDeCiudades estadisticas;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        estadisticas = new EstadisticasDeCiudades(cantCiudades);
        masAntiguos = new Heap();
        masRedituables = new Heap();
    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // masRedituable.first()
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // masAntiguos.first()
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return estadisticas.obtenerSuperavit();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return estadisticas.obtenerMayoresGanancias();
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return estadisticas.obtenerMayoresPerdida();
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return estadisticas.obtenerGananciaPromedio();
    }

}
