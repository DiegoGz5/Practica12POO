package battleship;

public interface NetworkListener {
    void onMessageReceived(String mensaje);
    void onConnectionClosed();
    void onConnectionEstablished();
}
