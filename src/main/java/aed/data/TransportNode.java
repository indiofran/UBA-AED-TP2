package aed.data;

import aed.Traslado;
import aed.interfaces.PriorityQueueInterface.HandleInterface;;

public class TransportNode extends Traslado {
    HandleInterface handleOldest;
    HandleInterface handleProfitable;

    public TransportNode(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        super(id, origen, destino, gananciaNeta, timestamp);
    }

    public void updateOldest(HandleInterface handle) {
        this.handleOldest = handle;
    }

    public void updateProfitable(HandleInterface handle) {
        this.handleProfitable = handle;
    }

    public HandleInterface getHandleOldest() {
        return handleOldest;
    }

    public HandleInterface getHandleProfitable() {
        return handleProfitable;
    }
}
