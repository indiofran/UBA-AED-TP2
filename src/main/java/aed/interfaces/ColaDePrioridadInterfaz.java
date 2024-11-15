package aed.interfaces;

import java.util.ArrayList;

public interface ColaDePrioridadInterfaz<T> {
    public HandleInterface encolar(T elem);

    public T desencolar();

    public T obtenerMax();

    public T eliminar(HandleInterface handle);

    public void actualizar(HandleInterface handle, T elem);

    public T obtener(HandleInterface handle);

    public ArrayList<HandleInterface> obtenerHandles();

    public int tamaño();

    public interface HandleInterface {
        public int obtenerIndice();

        public void actualizarIndice(int i);
    }
}
