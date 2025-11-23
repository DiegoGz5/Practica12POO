package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleshipP2PTest {

    /**
     * Crea una instancia y verifica que no sea null.
     */
    @Test
    void testConstructor() {
        BattleshipP2P p2p = new BattleshipP2P();
        assertNotNull(p2p);
    }

    /**
     * Prueba configurarFlujos() usando sockets conectados en localhost.
     */
    @Test
    void testConfigurarFlujos() throws Exception {
        // Servidor local en puerto libre
        ServerSocket server = new ServerSocket(0);
        int puerto = server.getLocalPort();

        // Simula cliente
        new Thread(() -> {
            try {
                Socket cliente = new Socket("localhost", puerto);
            } catch (IOException ignored) { }
        }).start();

        Socket socket = server.accept();

        BattleshipP2P juego = new BattleshipP2P();
        setPrivateField(juego, "socket", socket);

        // Invocar método privado
        var m = BattleshipP2P.class.getDeclaredMethod("configurarFlujos");
        m.setAccessible(true);
        m.invoke(juego);

        PrintWriter salida = (PrintWriter) getPrivateField(juego, "salida");
        BufferedReader entrada = (BufferedReader) getPrivateField(juego, "entrada");

        assertNotNull(salida);
        assertNotNull(entrada);

        server.close();
    }

    /**
     * Prueba cerrarConexion() verificando que no lanza excepción.
     */
    @Test
    void testCerrarConexion() throws Exception {
        BattleshipP2P juego = new BattleshipP2P();

        Socket pair[] = crearParDeSockets();

        setPrivateField(juego, "socket", pair[0]);
        setPrivateField(juego, "entrada", new BufferedReader(new InputStreamReader(pair[0].getInputStream())));
        setPrivateField(juego, "salida", new PrintWriter(pair[0].getOutputStream()));

        // Ejecutar método privado
        var m = BattleshipP2P.class.getDeclaredMethod("cerrarConexion");
        m.setAccessible(true);

        assertDoesNotThrow(() -> m.invoke(juego));
    }

    // ------------------------ UTILIDADES -------------------------

    private static void setPrivateField(Object obj, String field, Object value) throws Exception {
        Field f = obj.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(obj, value);
    }

    private static Object getPrivateField(Object obj, String field) throws Exception {
        Field f = obj.getClass().getDeclaredField(field);
        f.setAccessible(true);
        return f.get(obj);
    }

    private static Socket[] crearParDeSockets() throws Exception {
        ServerSocket server = new ServerSocket(0);
        int puerto = server.getLocalPort();

        Socket[] sockets = new Socket[2];

        Thread t = new Thread(() -> {
            try {
                sockets[1] = new Socket("localhost", puerto);
            } catch (Exception ignored) {}
        });
        t.start();

        sockets[0] = server.accept();
        server.close();
        return sockets;
    }
}