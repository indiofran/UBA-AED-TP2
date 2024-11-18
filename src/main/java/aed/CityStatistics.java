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

// El constructor CityStatistics realiza múltiples operaciones secuenciales.
// Inicializa un array de City con una complejidad de O(c) donde es es la cantidad de ciudades.
// Luego, crea una PriorityQueue con las ciudades, lo que tiene una complejidad de O(c).
// Posteriormente, se obtienen los handles de la PriorityQueue y se actualizan para cada ciudad,
// lo que también tiene una complejidad de O(c).
// Finalmente, se inicializan las listas de ganancias y pérdidas, junto con otras variables,
// todas con complejidad O(1). Por lo tanto, la complejidad total del constructor es O(c).

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

    // El metodo refresh realiza varias actualizaciones secuenciales.
// Primero, actualiza estadísticas globales y de las listas de ganancias y pérdidas,
// con una complejidad lineal respecto al tamaño de las listas.
// Posteriormente, actualiza los valores de las ciudades (O(1) por operación)
// y refresca los handles en la PriorityQueue, con una complejidad O(log(t)) por cada refresh.
// por lo que puede estimarse como O(log(t)).
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
        if (dispatched == 0) {
            return 0;
        }
        return totalProfits / dispatched;
    }

}
