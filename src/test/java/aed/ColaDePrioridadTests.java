package aed;

import org.junit.jupiter.api.Test;

import aed.data.ColaDePrioridad;
import aed.interfaces.ColaDePrioridadInterfaz.HandleInterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ColaDePrioridadTests {
    @Test
    public void encolarElementos() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(Comparator.naturalOrder());

        assertEquals(pq.tamaño(), 0);

        pq.encolar(3);
        pq.encolar(2);
        pq.encolar(5);
        pq.encolar(1);
        pq.encolar(4);

        assertEquals(pq.tamaño(), 5);
        assertEquals(pq.obtenerMax(), 5);
        assertEquals(pq.desencolar(), 5);
        assertEquals(pq.desencolar(), 4);
        assertEquals(pq.desencolar(), 3);
        assertEquals(pq.desencolar(), 2);
        assertEquals(pq.desencolar(), 1);
        assertEquals(pq.tamaño(), 0);
    }

    @Test
    public void listaEnOrden() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(new Integer[]{5, 4, 3, 2, 1}, Comparator.naturalOrder());

        assertEquals(pq.tamaño(), 5);
        assertEquals(pq.obtenerMax(), 5);
        assertEquals(pq.desencolar(), 5);
        assertEquals(pq.desencolar(), 4);
        assertEquals(pq.desencolar(), 3);
        assertEquals(pq.desencolar(), 2);
        assertEquals(pq.desencolar(), 1);
        assertEquals(pq.tamaño(), 0);
    }

    @Test
    public void listaEnOrdenInverso() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(new Integer[]{1, 2, 3, 4, 5}, Comparator.naturalOrder());

        assertEquals(pq.tamaño(), 5);
        assertEquals(pq.desencolar(), 5);
        assertEquals(pq.desencolar(), 4);
        assertEquals(pq.desencolar(), 3);
        assertEquals(pq.desencolar(), 2);
        assertEquals(pq.desencolar(), 1);
        assertEquals(pq.tamaño(), 0);
    }

    @Test
    public void listaEnDesorden() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(new Integer[]{3, 5, 2, 4, 1}, Comparator.naturalOrder());

        assertEquals(pq.tamaño(), 5);
        assertEquals(pq.obtenerMax(), 5);
        assertEquals(pq.desencolar(), 5);
        assertEquals(pq.desencolar(), 4);
        assertEquals(pq.desencolar(), 3);
        assertEquals(pq.desencolar(), 2);
        assertEquals(pq.desencolar(), 1);
        assertEquals(pq.tamaño(), 0);
    }

    @Test
    public void accederConHandle() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        assertEquals(pq.tamaño(), 0);

        for (Integer elem : elems) {
            handles.add(pq.encolar(elem));
        }

        for (int i = 0; i < handles.size(); i++) {
            assertEquals(elems.get(i), pq.obtener(handles.get(i)));
        }
    }

    @Test
    public void eliminarConHandle() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        for (Integer elem : elems) {
            handles.add(pq.encolar(elem));
        }

        assertEquals(pq.tamaño(), elems.size());

        for (int i = 0; i < handles.size(); i++) {
            assertEquals(elems.get(i), pq.eliminar(handles.get(i)));
            assertEquals(pq.tamaño(), elems.size() - 1 - i);
        }
    }

    @Test
    public void actualizarConHandle() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> elems = new ArrayList<>(Arrays.asList(2, 4, 3, 1, 5));
        ArrayList<HandleInterface> handles = new ArrayList<>();

        for (Integer elem : elems) {
            handles.add(pq.encolar(elem));
        }

        for (Integer i = 0; i < handles.size(); i++) {
            pq.actualizar(handles.get(i), i);
        }

        for (int i = 4; i >= 0; i--) {
            assertEquals(i, pq.desencolar());
        }
    }

    @Test
    public void encolarYDesencolar() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<>(new Integer[]{2, 4, 5, 3, 6, 1}, Comparator.naturalOrder());

        pq.encolar(10);
        pq.encolar(1);
        assertEquals(pq.desencolar(), 10);
        assertEquals(pq.desencolar(), 6);
        assertEquals(pq.desencolar(), 5);
        pq.encolar(11);
        pq.encolar(14);
        pq.encolar(0);
        assertEquals(pq.desencolar(), 14);
        assertEquals(pq.desencolar(), 11);
        assertEquals(pq.desencolar(), 4);

        assertEquals(pq.tamaño(), 5);
    }

    @Test
    public void obtenerHandles() {
        ColaDePrioridad<Integer> pq = new ColaDePrioridad<Integer>(new Integer[]{2, 4, 3, 1, 5}, Comparator.naturalOrder());

        ArrayList<HandleInterface> handles = pq.obtenerHandles();

        assertEquals(5, pq.obtener(handles.get(0)));
        assertEquals(4, pq.obtener(handles.get(1)));
        assertEquals(3, pq.obtener(handles.get(2)));
        assertEquals(1, pq.obtener(handles.get(3)));
        assertEquals(2, pq.obtener(handles.get(4)));
    }
}
