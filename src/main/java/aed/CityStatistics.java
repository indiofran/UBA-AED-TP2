package aed;

import java.util.ArrayList;

import aed.data.City;
import aed.data.PriorityQueue;
import aed.interfaces.PriorityQueueInterface;
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
        ArrayList<PriorityQueueInterface.HandleInterface> surplusHandles = citySurplusQueue.getHandle();
        for (int i = 0; i < cities.length; i++) {
            City city = cities[i];
            PriorityQueueInterface.HandleInterface surplusHandle = surplusHandles.get(i);
            city.updateHandle(surplusHandle);
        }
        citiesProfits = new ArrayList<>();
        citiesLoses = new ArrayList<>();
        totalProfits = 0;
        dispatched = 0;
    }

    public void refresh(Traslado t) {
        // Actualizar estadisticas dado un traslado
        City ciudadOrigen = this.cities[t.origen];
        City ciudadDestino = this.cities[t.destino];

        // 1. Actualizo el estado de las ciudades
        ciudadOrigen.updateProfit(t.gananciaNeta);
        ciudadOrigen.updateLoses(t.gananciaNeta);

        // 2. Actualizo estadisticas globales
        this.totalProfits += t.gananciaNeta;
        this.dispatched++;

        // 3. Actualizo los array de ganancia y perdida
        // Ganancia:
        if (this.citiesProfits.isEmpty()) {
            this.citiesProfits.add(ciudadOrigen.getId());
        } else {
            int greatestProfit = this.cities[this.citiesProfits.get(0)].getProfit();
            if (ciudadOrigen.getProfit() > greatestProfit) {
                // Reemplazo con la nueva ciudad
                this.citiesProfits.clear();
                this.citiesProfits.add(ciudadOrigen.getId());
            }
            if (ciudadOrigen.getProfit() == greatestProfit) {
                // Añado si no existe ya
                if (!this.citiesProfits.contains(ciudadOrigen.getId())) {
                    this.citiesProfits.add(ciudadOrigen.getId());
                }
            }
        }

        // Perdida:
        if (this.citiesLoses.isEmpty()) {
            this.citiesLoses.add(ciudadDestino.getId());
        } else {
            int greatestLoss = this.cities[this.citiesLoses.get(0)].getLoses();
            if (ciudadDestino.getLoses() < greatestLoss) {
                // Reemplazo con la nueva ciudad
                this.citiesLoses.clear();
                this.citiesLoses.add(ciudadDestino.getId());
            }
            if (ciudadDestino.getLoses() == greatestLoss) {
                // Añado si no existe ya
                if (!this.citiesLoses.contains(ciudadDestino.getId())) {
                    this.citiesLoses.add(ciudadDestino.getId());
                }
            }
        }
    }

    public int getHighestSurplus() {
        return citySurplusQueue.peek().getSurplus();
    }

    public ArrayList<Integer> getHighestProfits() {
        return citiesProfits;
    }

    public ArrayList<Integer> getHighestLost() {
        return citiesLoses;
    }

    public int getAverageProfit() {
        return totalProfits / dispatched;
    }

}
