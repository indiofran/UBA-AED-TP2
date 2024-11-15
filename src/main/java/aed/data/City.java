package aed.data;

import aed.interfaces.PriorityQueueInterface;

public class City {
    int profit;
    int loses;
    int id;
    PriorityQueueInterface.HandleInterface handle;

    public City(int id, int profit, int loses){
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
}
