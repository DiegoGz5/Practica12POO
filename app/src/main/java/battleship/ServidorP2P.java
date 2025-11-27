package battleship;

import java.io.*;
import java.net.*;

public class ServidorP2P implements NetworkHandler, Runnable {
    private final int puerto;
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private NetworkListener listener;
    private Thread hilo;

    public ServidorP2P(int puerto) {
        this.puerto = puerto;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(puerto);
        hilo = new Thread(this, "ServidorP2P-Accept");
        hilo.start();
    }

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (listener != null) listener.onConnectionEstablished();

            // loop de lectura
            String line;
            while ((line = in.readLine()) != null) {
                if (listener != null) listener.onMessageReceived(line);
            }
        } catch (IOException e) {
            // conexión cerrada o error
        } finally {
            close();
            if (listener != null) listener.onConnectionClosed();
        }
    }

    @Override
    public void enviar(String mensaje) {
        if (out != null) {
            out.println(mensaje);
        }
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
        try { if (serverSocket != null) serverSocket.close(); } catch (Exception ignored) {}
    }
}
