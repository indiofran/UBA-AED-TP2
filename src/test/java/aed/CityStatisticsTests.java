package aed;

import org.junit.jupiter.api.Test;

import aed.data.TransportNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

public class CityStatisticsTests {
    CityStatistics stats;

    @BeforeEach
    void init() {
        this.stats = new CityStatistics(4);
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2)
                    encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void oneRefresh() {
        TransportNode transportNode = new TransportNode(0, 2, 3, 50, 1);
        this.stats.refresh(transportNode);

        assertSetEquals(new ArrayList<>(Arrays.asList(2)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), this.stats.getHighestLost());
        assertEquals(2, this.stats.getHighestSurplus());
        assertEquals(50, this.stats.getAverageProfit());
    }

    @Test
    void multipleRefreshes() {
        TransportNode transportNode1 = new TransportNode(0, 1, 3, 50, 1);
        TransportNode transportNode2 = new TransportNode(1, 0, 3, 20, 2);
        TransportNode transportNode3 = new TransportNode(2, 2, 0, 10, 3);
        TransportNode transportNode4 = new TransportNode(3, 3, 2, 70, 4);

        this.stats.refresh(transportNode1);
        this.stats.refresh(transportNode2);
        this.stats.refresh(transportNode3);
        this.stats.refresh(transportNode4);

        assertSetEquals(new ArrayList<>(Arrays.asList(3)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(3, 2)), this.stats.getHighestLost());
        assertEquals(1, this.stats.getHighestSurplus());
        assertEquals(37, this.stats.getAverageProfit());
    }

    @Test
    void multipleCitiesWithTheHighestProfit() {
        TransportNode transportNode1 = new TransportNode(0, 1, 3, 50, 1);
        TransportNode transportNode2 = new TransportNode(1, 0, 3, 50, 2);
        TransportNode transportNode3 = new TransportNode(2, 2, 0, 10, 3);
        TransportNode transportNode4 = new TransportNode(3, 3, 2, 30, 4);

        this.stats.refresh(transportNode1);
        this.stats.refresh(transportNode2);
        this.stats.refresh(transportNode3);
        this.stats.refresh(transportNode4);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 0)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), this.stats.getHighestLost());
        assertEquals(1, this.stats.getHighestSurplus());
        assertEquals(35, this.stats.getAverageProfit());
    }

    @Test
    void multipleCitiesWithTheHighestLoss() {
        TransportNode transportNode1 = new TransportNode(0, 1, 3, 25, 1);
        TransportNode transportNode2 = new TransportNode(1, 0, 1, 10, 2);
        TransportNode transportNode3 = new TransportNode(2, 1, 0, 30, 3);
        TransportNode transportNode4 = new TransportNode(3, 3, 2, 30, 4);

        this.stats.refresh(transportNode1);
        this.stats.refresh(transportNode2);
        this.stats.refresh(transportNode3);
        this.stats.refresh(transportNode4);

        assertSetEquals(new ArrayList<>(Arrays.asList(1)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 2)), this.stats.getHighestLost());
        assertEquals(1, this.stats.getHighestSurplus());
        assertEquals(23, this.stats.getAverageProfit());
    }

    @Test
    void multipleCitiesWithTheHighestSurplus() {
        TransportNode transportNode1 = new TransportNode(0, 1, 3, 25, 1);
        TransportNode transportNode2 = new TransportNode(1, 0, 1, 10, 2);
        TransportNode transportNode3 = new TransportNode(2, 1, 0, 30, 3);
        TransportNode transportNode4 = new TransportNode(3, 3, 2, 70, 4);

        this.stats.refresh(transportNode1);
        this.stats.refresh(transportNode2);
        this.stats.refresh(transportNode3);
        this.stats.refresh(transportNode4);

        assertSetEquals(new ArrayList<>(Arrays.asList(3)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(2)), this.stats.getHighestLost());
        assertEquals(1, this.stats.getHighestSurplus());
        assertEquals(33, this.stats.getAverageProfit());
    }

    @Test
    void allCitiesWithSameProfitsAndLoss() {
        TransportNode transportNode1 = new TransportNode(0, 0, 1, 50, 1);
        TransportNode transportNode2 = new TransportNode(1, 1, 2, 50, 2);
        TransportNode transportNode3 = new TransportNode(2, 2, 3, 50, 3);
        TransportNode transportNode4 = new TransportNode(3, 3, 0, 50, 4);

        this.stats.refresh(transportNode1);
        this.stats.refresh(transportNode2);
        this.stats.refresh(transportNode3);
        this.stats.refresh(transportNode4);

        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3)), this.stats.getHighestProfits());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3)), this.stats.getHighestLost());
        assertEquals(0, this.stats.getHighestSurplus());
        assertEquals(50, this.stats.getAverageProfit());
    }
}
