package battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TableroTest {

    @Test
    public void testInicializacion() {
        Tablero t = new Tablero();
        char[][] g = t.getGrid();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals('~', g[i][j], "El tablero debe iniciar con ~");
            }
        }
    }

    @Test
    public void testColocarBarcoHorizontalCorrecto() {
        Tablero t = new Tablero();
        boolean ok = t.colocarBarco(2, 2, 3, true);
        assertTrue(ok);

        char[][] g = t.getGrid();
        assertEquals('B', g[2][2]);
        assertEquals('B', g[2][3]);
        assertEquals('B', g[2][4]);
    }

    @Test
    public void testColocarBarcoVerticalCorrecto() {
        Tablero t = new Tablero();
        boolean ok = t.colocarBarco(1, 1, 4, false);
        assertTrue(ok);

        char[][] g = t.getGrid();
        assertEquals('B', g[1][1]);
        assertEquals('B', g[2][1]);
        assertEquals('B', g[3][1]);
        assertEquals('B', g[4][1]);
    }

    @Test
    public void testColocarBarcoFueraHorizontal() {
        Tablero t = new Tablero();
        boolean ok = t.colocarBarco(0, 8, 4, true);
        assertFalse(ok); // se sale del tablero
    }

    @Test
    public void testColocarBarcoFueraVertical() {
        Tablero t = new Tablero();
        boolean ok = t.colocarBarco(9, 0, 2, false);
        assertFalse(ok);
    }

    @Test
    public void testColisionDeBarcos() {
        Tablero t = new Tablero();
        t.colocarBarco(0, 0, 3, true);

        boolean ok = t.colocarBarco(0, 2, 3, true);
        assertFalse(ok); // colisiona con el primero
    }

    @Test
    public void testDisparoAgua() {
        Tablero t = new Tablero();
        String res = t.disparar(0, 0);
        assertEquals("agua", res);

        assertEquals('O', t.getGrid()[0][0]);
    }

    @Test
    public void testDisparoImpacto() {
        Tablero t = new Tablero();
        t.colocarBarco(0, 0, 2, true);

        String res = t.disparar(0, 0);
        assertEquals("impacto", res);

        assertEquals('X', t.getGrid()[0][0]);
    }

    @Test
    public void testDisparoRepetido() {
        Tablero t = new Tablero();
        t.disparar(0, 0); // primera vez

        String res = t.disparar(0, 0); // repetido
        assertEquals("repetido", res);
    }

    @Test
    public void testTodosHundidos() {
        Tablero t = new Tablero();
        t.colocarBarco(0, 0, 2, true);

        assertFalse(t.todosHundidos());

        t.disparar(0, 0);
        assertFalse(t.todosHundidos());

        t.disparar(0, 1);
        assertTrue(t.todosHundidos());
    }
}
