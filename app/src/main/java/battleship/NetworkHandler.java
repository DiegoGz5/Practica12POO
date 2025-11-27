package battleship;

public interface NetworkHandler {
    void enviar(String mensaje);
    void setListener(NetworkListener listener);
    void close();
}
