package aed;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ComplexityBestEffortTest {
    public static void main(String[] args) {
        int maxSize = 1_000; // Máximo tamaño de traslados a probar
        int maxCities = 100; // Máximo número de ciudades aleatorias
        Random random = new Random();

        // Arrays para resultados
        int[] inputSizes = new int[maxSize];
        int[] cityCounts = new int[maxSize];
        long[] constructorTimes = new long[maxSize];
        long[] registerTimes = new long[maxSize];
        long[] dispatchProfitableTimes = new long[maxSize];
        long[] dispatchOldestTimes = new long[maxSize];
        long[] superavitTimes = new long[maxSize];
        long[] gainTimes = new long[maxSize];
        long[] lossTimes = new long[maxSize];
        long[] avgProfitTimes = new long[maxSize];

        for (int size = 1; size <= maxSize; size++) {
            // Generar número aleatorio de ciudades
            int numCities = random.nextInt(maxCities) + 1;

            // Generar traslados
            Traslado[] traslados = generateTraslados(size, numCities);

            // Medir tiempo del constructor
            long startTime = System.nanoTime();
            BestEffort bestEffort = new BestEffort(numCities, traslados);
            long endTime = System.nanoTime();
            constructorTimes[size - 1] = (endTime - startTime) / 1_000_000; // Convertir a ms

            // Registrar traslados
            Traslado[] nuevosTraslados = generateTraslados(size / 2, numCities);
            startTime = System.nanoTime();
            bestEffort.registrarTraslados(nuevosTraslados);
            endTime = System.nanoTime();
            registerTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Despachar más redituables
            startTime = System.nanoTime();
            bestEffort.despacharMasRedituables(size / 4);
            endTime = System.nanoTime();
            dispatchProfitableTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Despachar más antiguos
            startTime = System.nanoTime();
            bestEffort.despacharMasAntiguos(size / 4);
            endTime = System.nanoTime();
            dispatchOldestTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Consultas estadísticas
            startTime = System.nanoTime();
            bestEffort.ciudadConMayorSuperavit();
            endTime = System.nanoTime();
            superavitTimes[size - 1] = (endTime - startTime);

            startTime = System.nanoTime();
            bestEffort.ciudadesConMayorGanancia();
            endTime = System.nanoTime();
            gainTimes[size - 1] = (endTime - startTime);

            startTime = System.nanoTime();
            bestEffort.ciudadesConMayorPerdida();
            endTime = System.nanoTime();
            lossTimes[size - 1] = (endTime - startTime);

            startTime = System.nanoTime();
            bestEffort.gananciaPromedioPorTraslado();
            endTime = System.nanoTime();
            avgProfitTimes[size - 1] = (endTime - startTime);

            // Guardar progreso
            inputSizes[size - 1] = size;
            cityCounts[size - 1] = numCities;

            if (size % 100 == 0) {
                System.out.println("Procesado: " + size + " entradas, ciudades: " + numCities);
            }
        }

        // Guardar resultados en archivos separados
        saveResultsToCSV("constructor_times.csv", inputSizes, cityCounts, constructorTimes);
        saveResultsToCSV("register_times.csv", inputSizes, cityCounts, registerTimes);
        saveResultsToCSV("dispatch_profitable_times.csv", inputSizes, cityCounts, dispatchProfitableTimes);
        saveResultsToCSV("dispatch_oldest_times.csv", inputSizes, cityCounts, dispatchOldestTimes);
        saveResultsToCSV("superavit_times.csv", inputSizes, cityCounts, superavitTimes);
        saveResultsToCSV("gain_times.csv", inputSizes, cityCounts, gainTimes);
        saveResultsToCSV("loss_times.csv", inputSizes, cityCounts, lossTimes);
        saveResultsToCSV("avg_profit_times.csv", inputSizes, cityCounts, avgProfitTimes);
    }

    // Generar traslados aleatorios con origen, destino, ganancia y timestamp
    private static Traslado[] generateTraslados(int size, int numCities) {
        Traslado[] traslados = new Traslado[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int origen = random.nextInt(numCities);
            int destino;
            do {
                destino = random.nextInt(numCities);
            } while (origen == destino); // Evitar que origen y destino sean iguales

            traslados[i] = new Traslado(
                    i,                      // ID único
                    origen,                 // Ciudad origen
                    destino,                // Ciudad destino
                    random.nextInt(1000),   // Ganancia neta
                    (int) (System.currentTimeMillis() + i) // Timestamp único
            );
        }
        return traslados;
    }

    // Guardar resultados en CSV
    private static void saveResultsToCSV(String fileName, int[] inputSizes, int[] cityCounts, long[] times) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("InputSize,CityCount,Time\n");
            for (int i = 0; i < inputSizes.length; i++) {
                writer.append(inputSizes[i] + "," + cityCounts[i] + "," + times[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
