package aed.interfaces;

import java.util.ArrayList;

public interface PriorityQueueInterface<T> {
    public HandleInterface add(T elem);

    public T poll();

    public T peek();

    public T remove(HandleInterface handle);

    public void refresh(HandleInterface handle, T elem);

    public T get(HandleInterface handle);

    public ArrayList<HandleInterface> getHandle();

    public int size();

    public interface HandleInterface {
        public int getIndex();

        public void refreshIndex(int i);
    }
}
