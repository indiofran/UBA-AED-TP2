package aed.data;

import aed.interfaces.PriorityQueueInterface.HandleInterface;;

public class City {
    int profit;
    int loses;
    int id;
    HandleInterface handle;

    public City(int id, int profit, int loses) {
        this.id = id;
        this.profit = profit;
        this.loses = loses;
    }

    public int getProfit() {
        return profit;
    }

    public int getLoses() {
        return loses;
    }

    public int getId() {
        return id;
    }

    public int getSurplus() {
        return profit - loses;
    }

    public HandleInterface getHandle() {
        return this.handle;
    }

    public void updateHandle(HandleInterface handle) {
        this.handle = handle;
    }

    public void updateProfit(int profit) {
        this.profit += profit;
    }

    public void updateLoses(int loses) {
        this.loses += loses;
    }
}
