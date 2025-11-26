package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero();
    }

    @Test
    void testColocarBarcoCorrectamente() {
        boolean resultado = tablero.colocarBarco(0, 0, 3, true);
        assertTrue(resultado, "El barco debería colocarse correctamente");
        char[][] grid = tablero.getGrid();
        assertEquals('B', grid[0][0]);
        assertEquals('B', grid[0][1]);
        assertEquals('B', grid[0][2]);
    }

    @Test
    void testColocarBarcoFueraDeRango() {
        boolean resultado = tablero.colocarBarco(9, 8, 3, true);
        assertFalse(resultado, "El barco no debería colocarse fuera del tablero");
    }

    @Test
    void testColocarBarcoConColision() {
        tablero.colocarBarco(0, 0, 3, true);
        boolean resultado = tablero.colocarBarco(0, 2, 3, true); // choca con el anterior
        assertFalse(resultado, "No debería permitir colocar un barco encima de otro");
    }

    @Test
    void testDispararAgua() {
        String resultado = tablero.disparar(0, 0);
        assertEquals("agua", resultado);
        assertEquals('O', tablero.getGrid()[0][0]);
    }

    @Test
    void testDispararImpacto() {
        tablero.colocarBarco(0, 0, 1, true);
        String resultado = tablero.disparar(0, 0);
        assertEquals("impacto", resultado);
        assertEquals('X', tablero.getGrid()[0][0]);
    }

    @Test
    void testDispararRepetido() {
        tablero.disparar(0, 0);
        String resultado = tablero.disparar(0, 0);
        assertEquals("repetido", resultado);
    }

    @Test
    void testTodosHundidos() {
        tablero.colocarBarco(0, 0, 1, true);
        tablero.colocarBarco(1, 0, 1, true);

        assertFalse(tablero.todosHundidos());

        tablero.disparar(0, 0);
        tablero.disparar(1, 0);

        assertTrue(tablero.todosHundidos());
    }

    @Test
    void testColocarBarcosAutomaticamente() {
        Tablero t2 = new Tablero();
        t2.colocarBarcosAutomaticamente();
        // No se puede predecir exacto, pero todosHundidos debe ser false al inicio
        assertFalse(t2.todosHundidos());
    }
}
