package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;

public class CityStatistics {

    private ArrayList<Ciudades> cities;
    private PriorityQueue<Ciudades> citySurplusQueue;
    private ArrayList<Integer> citiesProfits;
    private ArrayList<Integer> citiesLoses;
    private int totalProfits;
    private int dispatched;

    public CityStatistics(int numberCities) {
        //
    }

    public void refresh(Traslado t) {
        // Actualizar estadisticas dado un traslado
    }

    public int getSurplus() {
        return 0;
    }

    public ArrayList<Integer> getHigherProfits() {
        return citiesProfits;
    }

    public ArrayList<Integer> getHigherLost() {
        return citiesLoses;
    }

    public int getAverageProfit() {
        return totalProfits / dispatched;
    }

}
