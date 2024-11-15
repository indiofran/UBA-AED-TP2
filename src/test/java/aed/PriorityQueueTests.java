package aed;

import org.junit.jupiter.api.Test;

import aed.data.PriorityQueue;
import aed.interfaces.PriorityQueueInterface.HandleInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueueTests {
    @Test
    public void addElementos() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());

        assertEquals(pq.size(), 0);

        pq.add(3);
        pq.add(2);
        pq.add(5);
        pq.add(1);
        pq.add(4);

        assertEquals(pq.size(), 5);
        assertEquals(pq.peek(), 5);
        assertEquals(pq.poll(), 5);
        assertEquals(pq.poll(), 4);
        assertEquals(pq.poll(), 3);
        assertEquals(pq.poll(), 2);
        assertEquals(pq.poll(), 1);
        assertEquals(pq.size(), 0);
    }

    @Test
    public void listOrdered() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Integer[]{5, 4, 3, 2, 1}, Comparator.naturalOrder());

        assertEquals(pq.size(), 5);
        assertEquals(pq.peek(), 5);
        assertEquals(pq.poll(), 5);
        assertEquals(pq.poll(), 4);
        assertEquals(pq.poll(), 3);
        assertEquals(pq.poll(), 2);
        assertEquals(pq.poll(), 1);
        assertEquals(pq.size(), 0);
    }

    @Test
    public void listInReverseOrder() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Integer[]{1, 2, 3, 4, 5}, Comparator.naturalOrder());

        assertEquals(pq.size(), 5);
        assertEquals(pq.poll(), 5);
        assertEquals(pq.poll(), 4);
        assertEquals(pq.poll(), 3);
        assertEquals(pq.poll(), 2);
        assertEquals(pq.poll(), 1);
        assertEquals(pq.size(), 0);
    }

    @Test
    public void listNotOrdered() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Integer[]{3, 5, 2, 4, 1}, Comparator.naturalOrder());

        assertEquals(pq.size(), 5);
        assertEquals(pq.peek(), 5);
        assertEquals(pq.poll(), 5);
        assertEquals(pq.poll(), 4);
        assertEquals(pq.poll(), 3);
        assertEquals(pq.poll(), 2);
        assertEquals(pq.poll(), 1);
        assertEquals(pq.size(), 0);
    }

    @Test
    public void getWithHandle() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        assertEquals(pq.size(), 0);

        for (Integer elem : elems) {
            handles.add(pq.add(elem));
        }

        for (int i = 0; i < handles.size(); i++) {
            assertEquals(elems.get(i), pq.get(handles.get(i)));
        }
    }

    @Test
    public void removeWithHandle() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        for (Integer elem : elems) {
            handles.add(pq.add(elem));
        }

        assertEquals(pq.size(), elems.size());

        for (int i = 0; i < handles.size(); i++) {
            assertEquals(elems.get(i), pq.remove(handles.get(i)));
            assertEquals(pq.size(), elems.size() - 1 - i);
        }
    }

    @Test
    public void refreshWithHandle() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        for (Integer elem : elems) {
            handles.add(pq.add(elem));
        }

        for (Integer i = 0; i < handles.size(); i++) {
            pq.refresh(handles.get(i), i);
        }

        for (int i = 4; i >= 0; i--) {
            assertEquals(i, pq.poll());
        }
    }

    @Test
    public void addAndPoll() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Integer[]{2, 4, 5, 3, 6, 1}, Comparator.naturalOrder());

        pq.add(10);
        pq.add(1);
        assertEquals(pq.poll(), 10);
        assertEquals(pq.poll(), 6);
        assertEquals(pq.poll(), 5);
        pq.add(11);
        pq.add(14);
        pq.add(0);
        assertEquals(pq.poll(), 14);
        assertEquals(pq.poll(), 11);
        assertEquals(pq.poll(), 4);

        assertEquals(pq.size(), 5);
    }

    @Test
    public void getHandle() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Integer[]{2, 4, 3, 1, 5}, Comparator.naturalOrder());

        ArrayList<HandleInterface> handles = pq.getHandle();

        assertEquals(5, pq.get(handles.get(0)));
        assertEquals(4, pq.get(handles.get(1)));
        assertEquals(3, pq.get(handles.get(2)));
        assertEquals(1, pq.get(handles.get(3)));
        assertEquals(2, pq.get(handles.get(4)));
    }
}
