package battleship;

import battleship.Juego;
import battleship.Tablero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class JuegoTest {

    @Test
    void testVictoriaJugador1() {
        Tablero t1 = new Tablero();
        Tablero t2 = new Tablero();

        // Colocar un barco de tamaño 1 en (0,0)
        t1.colocarBarco(0,0,1,true);
        t2.colocarBarco(0,0,1,true);

        Juego juego = new Juego(t1, t2);

        // Simular disparos: jugador1 dispara a (0,0) y gana
        List<int[]> secuencia1 = List.of(new int[]{0,0});
        List<int[]> secuencia2 = List.of(new int[]{0,0}); // aunque dispara, ya perdió jugador2

        int ganador = juego.jugarConSecuencia(secuencia1, secuencia2);
        assertEquals(1, ganador, "Jugador 1 debería ganar");
    }

    @Test
    void testVictoriaJugador2() {
        Tablero t1 = new Tablero();
        Tablero t2 = new Tablero();

        t1.colocarBarco(0,0,1,true);
        t2.colocarBarco(0,0,1,true);

        Juego juego = new Juego(t1, t2);

        // Simular disparos: jugador2 dispara primero y gana
        List<int[]> secuencia1 = List.of(new int[]{1,1}); // fallo
        List<int[]> secuencia2 = List.of(new int[]{0,0}); // acierto

        int ganador = juego.jugarConSecuencia(secuencia1, secuencia2);
        assertEquals(2, ganador, "Jugador 2 debería ganar");
    }
}
