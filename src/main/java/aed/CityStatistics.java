package aed;

import java.util.ArrayList;

import aed.data.City;
import aed.data.PriorityQueue;
import aed.utils.CitySurplusComparator;

public class CityStatistics {

    private City[] cities;
    private PriorityQueue<City> citySurplusQueue;
    private ArrayList<Integer> citiesProfits;
    private ArrayList<Integer> citiesLoses;
    private int totalProfits;
    private int dispatched;

    public CityStatistics(int cityQuatity) {
        cities = new City[cityQuatity];
        for (int i = 0; i < cityQuatity; i++) {
            City city = new City(i,0,0);
            cities[i]=city;
        }
        citySurplusQueue = new PriorityQueue<>(cities, new CitySurplusComparator());
        citiesProfits = new ArrayList<>();
        citiesLoses = new ArrayList<>();
        totalProfits = 0;
        dispatched = 0;
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
