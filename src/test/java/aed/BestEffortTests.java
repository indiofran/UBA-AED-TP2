package aed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init() {
        // Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 400, 20),
                new Traslado(3, 3, 4, 500, 50),
                new Traslado(4, 4, 3, 500, 11),
                new Traslado(5, 1, 0, 1000, 40),
                new Traslado(6, 1, 0, 1000, 41),
                new Traslado(7, 6, 3, 2000, 42)
        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2)
                    encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_con_mas_ganancia_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mas_viejo_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_mas_viejo_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mixtos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void agregar_traslados() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 0, 1, 10001, 5),
                new Traslado(9, 0, 1, 40000, 2),
                new Traslado(10, 0, 1, 50000, 3),
                new Traslado(11, 0, 1, 50000, 4),
                new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void promedio_por_traslado() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 1, 2, 1452, 5),
                new Traslado(9, 1, 2, 334, 2),
                new Traslado(10, 1, 2, 24, 3),
                new Traslado(11, 1, 2, 333, 4),
                new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());

    }

    @Test
    void mayor_superavit() {
        Traslado[] nuevos = new Traslado[] {
                new Traslado(1, 3, 4, 1, 7),
                new Traslado(7, 6, 5, 40, 6),
                new Traslado(6, 5, 6, 3, 5),
                new Traslado(2, 2, 1, 41, 4),
                new Traslado(3, 3, 4, 100, 3),
                new Traslado(4, 1, 2, 30, 2),
                new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());
    }

    @Test
    void despachar_todos_mas_redituables() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        int[] ids = sis.despacharMasRedituables(this.cantCiudades);

        assertArrayEquals(ids, new int[] { 7, 5, 6, 3, 4, 2, 1 }); // Ordenados de forma decreciente por ganancia
        assertSetEquals(sis.ciudadesConMayorGanancia(), new ArrayList<>(Arrays.asList(1, 6)));
        assertSetEquals(sis.ciudadesConMayorPerdida(), new ArrayList<>(Arrays.asList(3)));
        assertEquals(sis.ciudadConMayorSuperavit(), 6);
        assertEquals(sis.gananciaPromedioPorTraslado(), 785);
    }

    @Test
    void despachar_todos_mas_antiguos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        int[] ids = sis.despacharMasAntiguos(this.cantCiudades);

        assertArrayEquals(ids, new int[] { 1, 4, 2, 5, 6, 7, 3 }); // Ordenados de forma creciente por timestamp
        assertSetEquals(sis.ciudadesConMayorGanancia(), new ArrayList<>(Arrays.asList(1, 6)));
        assertSetEquals(sis.ciudadesConMayorPerdida(), new ArrayList<>(Arrays.asList(3)));
        assertEquals(sis.ciudadConMayorSuperavit(), 6);
        assertEquals(sis.gananciaPromedioPorTraslado(), 785);
    }

    @Test
    void despachar_mas_de_la_capacidad() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        int[] ids = sis.despacharMasRedituables(100);

        assertArrayEquals(ids, new int[] { 7, 5, 6, 3, 4, 2, 1 });
    }

    @Test
    void despachar_muchos() {
        int nuevaCantCiudades = 100;
        int cantTraslados = (nuevaCantCiudades - 1) * 1000; // Así se distribuyen los destinos uniformemente
        int ganancia = 500;
        Traslado[] nuevosTraslados = new Traslado[cantTraslados];

        for (int i = 0; i < cantTraslados; i++) {
            int ciudadDestinoId = (i % (nuevaCantCiudades - 1)) + 1; // Distribución uniforme desde 1 hasta nuevaCantCiudades - 1
            nuevosTraslados[i] = new Traslado(i, 0, ciudadDestinoId, ganancia, i + 1);
        }

        BestEffort sis = new BestEffort(nuevaCantCiudades, nuevosTraslados);
        sis.despacharMasAntiguos(cantTraslados);
        assertEquals(sis.ciudadConMayorSuperavit(), 0);
        assertEquals(sis.gananciaPromedioPorTraslado(), ganancia);
        assertEquals(sis.ciudadesConMayorGanancia(), new ArrayList<>(Arrays.asList(0)));

        ArrayList<Integer> ciudadesPerdidas = new ArrayList<>();
        for (int i = 1; i <= 99; i++) {
            ciudadesPerdidas.add(i);
        }
        assertSetEquals(sis.ciudadesConMayorPerdida(), ciudadesPerdidas); // Todas excepto la primera con misma perdida
    }
}
