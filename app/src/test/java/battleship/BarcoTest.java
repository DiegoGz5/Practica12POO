package battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BarcoTest {

    @Test
    void testRegistrarImpactoYEstaHundido() {
        Barco barco = new Barco(3);
        assertFalse(barco.estaHundido());

        barco.registrarImpacto();
        assertFalse(barco.estaHundido());

        barco.registrarImpacto();
        assertFalse(barco.estaHundido());

        barco.registrarImpacto();
        assertTrue(barco.estaHundido());
    }
}
