package aed;

import aed.data.PriorityQueue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;

public class ComplexityQueueTest {
    public static void main(String[] args) {
        int maxSize = 100_000; // Máximo número de elementos en la cola
        int maxValue = 10_000; // Máximo valor para los enteros en la cola
        Random random = new Random();

        // Arrays para guardar tiempos
        int[] inputSizes = new int[maxSize];
        long[] constructorTimes = new long[maxSize];
        long[] addTimes = new long[maxSize];
        long[] pollTimes = new long[maxSize];
        long[] peekTimes = new long[maxSize];
        long[] removeTimes = new long[maxSize];
        long[] refreshTimes = new long[maxSize];

        for (int size = 1; size <= maxSize; size++) {
            inputSizes[size - 1] = size;

            // Generar elementos aleatorios
            Integer[] elements = generateRandomArray(size, maxValue);

            // Medir tiempo del constructor
            long startTime = System.nanoTime();
            PriorityQueue<Integer> pq = new PriorityQueue<>(elements, Comparator.naturalOrder());
            long endTime = System.nanoTime();
            constructorTimes[size - 1] = (endTime - startTime) / 1_000_000; // Convertir a ms

            // Medir tiempo de `add`
            startTime = System.nanoTime();
            pq.add(random.nextInt(maxValue));
            endTime = System.nanoTime();
            addTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Medir tiempo de `poll`
            startTime = System.nanoTime();
            pq.poll();
            endTime = System.nanoTime();
            pollTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Medir tiempo de `peek`
            startTime = System.nanoTime();
            pq.peek();
            endTime = System.nanoTime();
            peekTimes[size - 1] = (endTime - startTime);

            // Medir tiempo de `remove`
            startTime = System.nanoTime();
            pq.remove(pq.getHandle().get(0)); // Eliminar el primer elemento
            endTime = System.nanoTime();
            removeTimes[size - 1] = (endTime - startTime) / 1_000_000;

            // Medir tiempo de `refresh`
            Integer updatedValue = random.nextInt(maxValue);
            startTime = System.nanoTime();
            pq.refresh(pq.getHandle().get(0), updatedValue);
            endTime = System.nanoTime();
            refreshTimes[size - 1] = (endTime - startTime) / 1_000_000;

            if (size % 100 == 0) {
                System.out.println("Procesado: " + size + " elementos.");
            }
        }

        // Guardar resultados en archivos CSV
        saveResultsToCSV("constructor_times.csv", inputSizes, constructorTimes);
        saveResultsToCSV("add_times.csv", inputSizes, addTimes);
        saveResultsToCSV("poll_times.csv", inputSizes, pollTimes);
        saveResultsToCSV("peek_times.csv", inputSizes, peekTimes);
        saveResultsToCSV("remove_times.csv", inputSizes, removeTimes);
        saveResultsToCSV("refresh_times.csv", inputSizes, refreshTimes);
    }

    // Generar un array de números aleatorios
    private static Integer[] generateRandomArray(int size, int maxValue) {
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue);
        }
        return array;
    }

    // Guardar resultados en un archivo CSV
    private static void saveResultsToCSV(String fileName, int[] inputSizes, long[] times) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("InputSize,Time\n");
            for (int i = 0; i < inputSizes.length; i++) {
                writer.append(inputSizes[i] + "," + times[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}