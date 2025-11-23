package battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BarcoTest {

    @Test
    public void testCrearBarco() {
        Barco barco = new Barco(3);
        assertEquals(3, barco.getTamanio());
        assertEquals(0, barco.getImpactos());
    }

    @Test
    public void testRegistrarImpacto() {
        Barco barco = new Barco(2);

        barco.registrarImpacto();
        assertEquals(1, barco.getImpactos());

        barco.registrarImpacto();
        assertEquals(2, barco.getImpactos());
    }

    @Test
    public void testNoRegistrarImpactosExtra() {
        Barco barco = new Barco(2);

        barco.registrarImpacto();
        barco.registrarImpacto();

        // Intento exceder impactos
        barco.registrarImpacto();
        barco.registrarImpacto();

        assertEquals(2, barco.getImpactos(), "No debe superar el tamaño del barco");
    }

    @Test
    public void testBarcoHundido() {
        Barco barco = new Barco(2);

        assertFalse(barco.estaHundido());

        barco.registrarImpacto();
        assertFalse(barco.estaHundido());

        barco.registrarImpacto();
        assertTrue(barco.estaHundido());
    }
}
