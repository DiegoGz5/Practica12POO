package battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    void testColocarBarcoYDisparar() {
        Tablero tablero = new Tablero();
        
        // Colocamos un barco horizontal de tamaño 3
        assertTrue(tablero.colocarBarco(0, 0, 3, true));

        // Intento colocar otro barco encima (debería fallar)
        assertFalse(tablero.colocarBarco(0, 0, 2, true));

        // Disparos
        assertEquals("impacto", tablero.disparar(0, 0));
        assertEquals("impacto", tablero.disparar(0, 1));
        assertEquals("impacto", tablero.disparar(0, 2));

        // Disparar agua
        assertEquals("agua", tablero.disparar(5, 5));

        // Disparar repetido
        assertEquals("repetido", tablero.disparar(0, 0));
    }

    @Test
    void testTodosHundidos() {
        Tablero tablero = new Tablero();
        tablero.colocarBarco(0, 0, 2, true);
        assertFalse(tablero.todosHundidos());

        tablero.disparar(0,0);
        assertFalse(tablero.todosHundidos());

        tablero.disparar(0,1);
        assertTrue(tablero.todosHundidos());
    }
}
