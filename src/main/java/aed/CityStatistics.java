package aed;

import java.util.ArrayList;

import aed.data.PriorityQueue;
import aed.utils.SurplusComparator;

public class CityStatistics {

    private Ciudad[] cities;
    private PriorityQueue<Ciudad> citySurplusQueue;
    private ArrayList<Integer> citiesProfits;
    private ArrayList<Integer> citiesLoses;
    private int totalProfits;
    private int dispatched;

    public CityStatistics(Ciudad[] cities) {
        // Asumo que cities ya viene ordenado donde cada indice coincide con su id (va de 0 a length - 1)
        this.cities = cities;
        this.citySurplusQueue = new PriorityQueue<>(cities, new SurplusComparator());
        this.citiesProfits = new ArrayList<>();
        this.citiesLoses = new ArrayList<>();
        this.totalProfits = 0;
        this.dispatched = 0;
    }

    public void refresh(Traslado t) {
        // Actualizar estadisticas dado un traslado
        Ciudad ciudadOrigen = this.cities[t.origen];
        Ciudad ciudadDestino = this.cities[t.destino];

        // 1. Actualizo el estado de las ciudades
        ciudadOrigen.ganancia += t.gananciaNeta;
        ciudadOrigen.perdida += t.gananciaNeta;

        // 2. Actualizo estadisticas globales
        this.totalProfits += t.gananciaNeta;
        this.dispatched++;

        // 3. Actualizo los array de ganancia y perdida
        // Ganancia:
        if (this.citiesProfits.isEmpty()) {
            this.citiesProfits.add(ciudadOrigen.id);
        } else {
            int greatestProfit = this.cities[this.citiesProfits.get(0)].ganancia;
            if (ciudadOrigen.ganancia > greatestProfit) {
                // Reemplazo con la nueva ciudad
                this.citiesProfits.clear();
                this.citiesProfits.add(ciudadOrigen.id);
            }
            if (ciudadOrigen.ganancia == greatestProfit) {
                // Añado si no existe ya
                if (!this.citiesProfits.contains(ciudadOrigen.id)) {
                    this.citiesProfits.add(ciudadOrigen.id);
                }
            }
        }

        // Perdida:
        if (this.citiesLoses.isEmpty()) {
            this.citiesLoses.add(ciudadDestino.id);
        } else {
            int greatestLoss = this.cities[this.citiesLoses.get(0)].perdida;
            if (ciudadDestino.perdida < greatestLoss) {
                // Reemplazo con la nueva ciudad
                this.citiesLoses.clear();
                this.citiesLoses.add(ciudadDestino.id);
            }
            if (ciudadDestino.perdida == greatestLoss) {
                // Añado si no existe ya
                if (!this.citiesLoses.contains(ciudadDestino.id)) {
                    this.citiesLoses.add(ciudadDestino.id);
                }
            }
        }
    }

    public int getHighestSurplus() {
        return this.citySurplusQueue.peek().getSuperavit();
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
