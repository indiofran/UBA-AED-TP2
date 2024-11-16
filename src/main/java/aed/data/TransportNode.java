package aed.data;

import aed.Traslado;
import aed.interfaces.PriorityQueueInterface;

public class TransportNode extends Traslado {
    PriorityQueueInterface.HandleInterface handleOldest;
    PriorityQueueInterface.HandleInterface handleProfitable;

    public TransportNode(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        super(id, origen, destino, gananciaNeta, timestamp);
    }

    public void updateOldest(PriorityQueueInterface.HandleInterface handle){
        this.handleOldest = handle;
    }
    public void updateProfitable(PriorityQueueInterface.HandleInterface handle){
        this.handleProfitable = handle;
    }
    public PriorityQueueInterface.HandleInterface getHandleOldest() {
        return handleOldest;
    }
    public PriorityQueueInterface.HandleInterface getHandleProfitable() {
        return handleProfitable;
    }
}
