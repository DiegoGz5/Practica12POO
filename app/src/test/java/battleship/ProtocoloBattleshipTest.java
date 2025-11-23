package battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProtocoloBattleshipTest {

    @Test
    public void testConstruirMensajeDisparo() {
        String msg = ProtocoloBattleship.construirMensajeDisparo(4, 7);
        assertEquals("DISPARAR|4,7", msg);
    }

    @Test
    public void testConstruirMensajeResultadoConBarco() {
        String msg = ProtocoloBattleship.construirMensajeResultado("IMPACTO", 3, 8, "CRUCERO");
        assertEquals("IMPACTO|3,8|CRUCERO", msg);
    }

    @Test
    public void testConstruirMensajeResultadoSinBarco() {
        String msg = ProtocoloBattleship.construirMensajeResultado("FALLO", 5, 2, null);
        assertEquals("FALLO|5,2", msg);
    }
}
