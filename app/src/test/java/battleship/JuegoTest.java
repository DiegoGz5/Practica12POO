package battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class JuegoTest {

    @Test
    public void testJugarConSecuencia() {
        // Crear tableros con barcos en posiciones fijas
        Tablero t1 = new Tablero();
        Tablero t2 = new Tablero();

        // Colocación fija de barcos para que los disparos sean predecibles
        t1.colocarBarco(0, 0, 2, true); // barco de 2 en fila 0, columnas 0-1
        t1.colocarBarco(1, 0, 3, true); // barco de 3 en fila 1, columnas 0-2

        t2.colocarBarco(0, 0, 2, true);
        t2.colocarBarco(1, 0, 3, true);

        // Crear el juego
        Juego juego = new Juego(t1, t2);

        // Secuencias de disparos que van a hundir todos los barcos
        List<int[]> secJugador1 = Arrays.asList(
                new int[]{0,0}, new int[]{0,1}, 
                new int[]{1,0}, new int[]{1,1}, new int[]{1,2}
        );

        List<int[]> secJugador2 = Arrays.asList(
                new int[]{0,0}, new int[]{0,1}, 
                new int[]{1,0}, new int[]{1,1}, new int[]{1,2}
        );

        // Ejecutar la simulación con secuencias
        int ganador = juego.jugarConSecuencia(secJugador1, secJugador2);

        // Comprobar que el ganador sea 1 (ya que jugador 1 dispara primero y completa todos los impactos)
        assertEquals(1, ganador);
    }
}
