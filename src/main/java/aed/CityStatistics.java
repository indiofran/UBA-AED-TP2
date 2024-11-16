package aed;

import java.util.ArrayList;

import aed.data.City;
import aed.data.PriorityQueue;
import aed.interfaces.PriorityQueueInterface.HandleInterface;
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
            City city = new City(i, 0, 0);
            cities[i] = city;
        }
        citySurplusQueue = new PriorityQueue<>(cities, new CitySurplusComparator());
        ArrayList<HandleInterface> surplusHandles = citySurplusQueue.getHandle();
        for (int i = 0; i < cities.length; i++) {
            City city = cities[i];
            HandleInterface surplusHandle = surplusHandles.get(i);
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

        // 1. Actualizo estadisticas globales
        this.totalProfits += t.gananciaNeta;
        this.dispatched++;

        // 2. Actualizo los array de ganancia y perdida
        // Ganancia:
        if (this.citiesProfits.isEmpty()) {
            this.citiesProfits.add(ciudadOrigen.getId());
        } else {
            int greatestProfit = this.cities[this.citiesProfits.get(0)].getProfit();
            if (ciudadOrigen.getProfit() + t.gananciaNeta > greatestProfit) {
                // Reemplazo con la nueva ciudad
                this.citiesProfits.clear();
                this.citiesProfits.add(ciudadOrigen.getId());
            }
            if (ciudadOrigen.getProfit() + t.gananciaNeta == greatestProfit) {
                this.citiesProfits.add(ciudadOrigen.getId());
            }
        }

        // Perdida:
        if (this.citiesLoses.isEmpty()) {
            this.citiesLoses.add(ciudadDestino.getId());
        } else {
            int greatestLoss = this.cities[this.citiesLoses.get(0)].getLoses();
            if (ciudadDestino.getLoses() + t.gananciaNeta > greatestLoss) {
                // Reemplazo con la nueva ciudad
                this.citiesLoses.clear();
                this.citiesLoses.add(ciudadDestino.getId());
            }
            if (ciudadDestino.getLoses() + t.gananciaNeta == greatestLoss) {
                this.citiesLoses.add(ciudadDestino.getId());
            }
        }

        // 3. Actualizo el estado de las ciudades
        ciudadOrigen.updateProfit(t.gananciaNeta);
        ciudadDestino.updateLoses(t.gananciaNeta);

        // 4. Actualiza la cola de prioridad de superavit
        this.citySurplusQueue.refresh(ciudadOrigen.getHandle(), ciudadOrigen);
        this.citySurplusQueue.refresh(ciudadDestino.getHandle(), ciudadDestino);
    }

    public int getHighestSurplus() {
        return citySurplusQueue.peek().getId();
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
