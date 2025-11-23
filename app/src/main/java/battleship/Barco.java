package battleship;
public class Barco {

    private int tamanio;
    private int impactos;

    public Barco(int tamanio) {
        this.tamanio = tamanio;
        this.impactos = 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getImpactos() {
        return impactos;
    }

    public void registrarImpacto() {
        if (impactos < tamanio) {
            impactos++;
        }
    }

    public boolean estaHundido() {
        return impactos >= tamanio;
    }
}
