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
    public int getId(){
        return id;
    }
    public int getSurplus() {
        return profit - loses;
    }

    public void updateHandle(PriorityQueueInterface.HandleInterface handle){
        this.handle = handle;
    }

    public void updateProfit(int profit){
        this.profit += profit;
    }
    public void updateLoses(int loses){
        this.loses += loses;
    }
}
