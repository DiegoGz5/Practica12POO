package battleship;

public interface InterfazJuego {
    void mostrarMensaje(String mensaje);
    int[] pedirCoordenadas(String jugador); // recibe nombre del jugador para mostrar
    void actualizarTablero(char[][] grid);
}
