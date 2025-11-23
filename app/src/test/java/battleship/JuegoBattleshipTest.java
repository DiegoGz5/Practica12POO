package battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JuegoBattleshipTest {

    @Test
    public void testInicializacionTableroPropio() {
        JuegoBattleship juego = new JuegoBattleship();

        // Al inicio TODO debe ser '~'
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals('~', obtenerPrivadoTableroPropio(juego)[i][j],
                        "El tablero propio debe iniciar vacío");
            }
        }
    }

    @Test
    public void testInicializacionTableroEnemigo() {
        JuegoBattleship juego = new JuegoBattleship();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals('?', obtenerPrivadoTableroEnemigo(juego)[i][j],
                        "El tablero enemigo debe iniciar con '?'");
            }
        }
    }

    @Test
    public void testColocarBarcosAutomaticamente() {
        JuegoBattleship juego = new JuegoBattleship();
        juego.colocarBarcosAutomaticamente();

        boolean hayBarcos = false;

        char[][] tab = obtenerPrivadoTableroPropio(juego);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tab[i][j] != '~') {
                    hayBarcos = true;
                }
            }
        }

        assertTrue(hayBarcos, "Después de colocar barcos debe haber al menos un barco colocado.");
    }

    @Test
    public void testImpacto() {
        JuegoBattleship juego = new JuegoBattleship();

        // colocar a mano un barco
        insertarBarco(juego, 0, 0, 'P');

        boolean impacto = juego.recibirDisparo(0, 0);
        assertTrue(impacto, "Debe ser impacto.");
    }

    @Test
    public void testFallo() {
        JuegoBattleship juego = new JuegoBattleship();
        boolean fallo = juego.recibirDisparo(5, 5);
        assertFalse(fallo, "Debe ser agua.");
    }

    @Test
    public void testRegistrarImpacto() {
        JuegoBattleship juego = new JuegoBattleship();
        juego.registrarImpacto(2, 3);

        char[][] tab = obtenerPrivadoTableroEnemigo(juego);

        assertEquals('X', tab[2][3]);
        assertTrue(juego.yaDisparado(2, 3));
    }

    @Test
    public void testRegistrarFallo() {
        JuegoBattleship juego = new JuegoBattleship();
        juego.registrarFallo(4, 6);

        char[][] tab = obtenerPrivadoTableroEnemigo(juego);

        assertEquals('O', tab[4][6]);
        assertTrue(juego.yaDisparado(4, 6));
    }

    @Test
    public void testBarcoHundido() {
        JuegoBattleship juego = new JuegoBattleship();

        insertarBarco(juego, 0, 0, 'D'); // Tamaño del destructor = 2

        juego.recibirDisparo(0, 0);
        juego.recibirDisparo(0, 1);

        assertTrue(juego.estaBarcoHundido("DESTRUCTOR"));
    }

    // --------------------  MÉTODOS DE APOYO  -----------------------

    private char[][] obtenerPrivadoTableroPropio(JuegoBattleship juego) {
        try {
            var field = JuegoBattleship.class.getDeclaredField("tableroPropio");
            field.setAccessible(true);
            return (char[][]) field.get(juego);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private char[][] obtenerPrivadoTableroEnemigo(JuegoBattleship juego) {
        try {
            var field = JuegoBattleship.class.getDeclaredField("tableroEnemigo");
            field.setAccessible(true);
            return (char[][]) field.get(juego);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insertarBarco(JuegoBattleship juego, int fila, int columna, char simbolo) {
        char[][] t = obtenerPrivadoTableroPropio(juego);
        t[fila][columna] = simbolo;
        t[fila][columna + 1] = simbolo;  // un barco mínimo de tamaño 2
    }
}
