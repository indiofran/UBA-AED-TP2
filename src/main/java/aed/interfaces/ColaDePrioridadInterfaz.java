package aed.interfaces;

public interface ColaDePrioridadInterfaz<T> {
    public void encolar(T elem);

    public T desencolar();

    public T obtenerMax();

    public int tamaño();
}
