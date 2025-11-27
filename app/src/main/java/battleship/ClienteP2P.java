package battleship;

import java.io.*;
import java.net.*;

public class ClienteP2P implements NetworkHandler, Runnable {
    private final String host;
    private final int puerto;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private NetworkListener listener;
    private Thread hilo;

    public ClienteP2P(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void start() throws IOException {
        socket = new Socket(host, puerto);
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (listener != null) listener.onConnectionEstablished();
        hilo = new Thread(this, "ClienteP2P-Read");
        hilo.start();
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                if (listener != null) listener.onMessageReceived(line);
            }
        } catch (IOException e) {
            // error o cierre
        } finally {
            close();
            if (listener != null) listener.onConnectionClosed();
        }
    }

    @Override
    public void enviar(String mensaje) {
        if (out != null) out.println(mensaje);
    }

    @Override
    public void setListener(NetworkListener listener) {
        this.listener = listener;
    }

    @Override
    public void close() {
        try { if (out != null) out.close(); } catch (Exception ignored) {}
        try { if (in != null) in.close(); } catch (Exception ignored) {}
        try { if (socket != null) socket.close(); } catch (Exception ignored) {}
    }
}
