package aed.interfaces;

public interface ColaDePrioridadHandlesInterfaz<T> {
    // Para manejarnos con handles lo mas eficiente es lo que nos permite un tipo T como Ciudades,
    // que como sus ids son consecutivas, podemos acceder a un elemento de la cola usando el id.

    /** Devuelve un handle del elemento encolado */
    public int encolar(T elem);

    /** Permite obtener un elemento cualquiera de la cola en O(1) dado un identificador unico del elemento T */
    public T obtener(int elem_id);

    public T desencolar();

    public T obtenerMax();

    public int tamaño();

    /** Devuelve los handles disponibles (todavía no se su utilidad) */
    public int[] handles();
}
