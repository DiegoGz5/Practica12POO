package battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class JuegoTest {

    @Test
    void testTurnosYDisparos() {
        Tablero t1 = new Tablero();
        Tablero t2 = new Tablero();

        // Colocamos un barco de 2 para pruebas rápidas
        t1.colocarBarco(0,0,2,true);
        t2.colocarBarco(1,1,2,true);

        // Simular entradas del usuario: disparos
        String input = "1\n1\n0\n0\n"; // Jugador1 dispara a (1,1), Jugador2 a (0,0)
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Juego juego = new Juego(t1, t2);

        // Ejecutamos solo un ciclo limitado (para test) usando un hilo
        Thread hilo = new Thread(juego::iniciar);
        hilo.start();
        try { Thread.sleep(500); hilo.interrupt(); } catch (InterruptedException e) {}

        // Verificar que los impactos fueron registrados
        assertEquals('X', t1.getGrid()[0][0]); // Jugador2 disparó a (0,0)
        assertEquals('X', t2.getGrid()[1][1]); // Jugador1 disparó a (1,1)
    }
}
