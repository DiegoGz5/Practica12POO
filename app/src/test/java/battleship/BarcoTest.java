package battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BarcoTest {

    @Test
    void testCreacionBarco() {
        Barco barco = new Barco(3);
        assertEquals(3, barco.getTamanio(), "El tamaño del barco debe ser 3");
        assertEquals(0, barco.getImpactos(), "Un barco nuevo no debe tener impactos");
    }

    @Test
    void testRegistrarImpacto() {
        Barco barco = new Barco(2);
        barco.registrarImpacto();
        assertEquals(1, barco.getImpactos(), "Debe registrar un impacto");
        barco.registrarImpacto();
        assertEquals(2, barco.getImpactos(), "Debe registrar el segundo impacto");
        // No debe exceder el tamaño
        barco.registrarImpacto();
        assertEquals(2, barco.getImpactos(), "Los impactos no deben superar el tamaño del barco");
    }

    @Test
    void testEstaHundido() {
        Barco barco = new Barco(2);
        assertFalse(barco.estaHundido(), "Inicialmente el barco no debe estar hundido");
        barco.registrarImpacto();
        assertFalse(barco.estaHundido(), "Con un impacto, aún no debe estar hundido");
        barco.registrarImpacto();
        assertTrue(barco.estaHundido(), "Con impactos igual al tamaño, el barco debe estar hundido");
    }
}
