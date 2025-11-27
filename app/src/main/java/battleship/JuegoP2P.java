package battleship;

import javax.swing.*;

public class JuegoP2P implements NetworkListener {

    private final Tablero miTablero;
    private final Tablero tableroEnemigo;
    private final NetworkHandler conexion;
    private BattleshipGUIOnline gui;

    private volatile boolean miTurno;
    private volatile boolean esperandoResultado = false;
    private int ultimoShotFila = -1;
    private int ultimoShotCol = -1;
    private volatile boolean gameOver = false;

    public JuegoP2P(Tablero miTablero, NetworkHandler conexion, boolean soyHost) {
        this.miTablero = miTablero;
        this.tableroEnemigo = new Tablero(); 
        this.conexion = conexion;
        conexion.setListener(this);
        this.miTurno = soyHost;
    }

    public void setGUI(BattleshipGUIOnline gui) {
        this.gui = gui;
        SwingUtilities.invokeLater(() ->
                gui.actualizarDesdeTableros(miTablero.getGrid(), tableroEnemigo.getGrid(), miTurno));
    }

    public boolean intentarDisparo(int fila, int col) {
        if (!miTurno || esperandoResultado || gameOver) return false;

        ultimoShotFila = fila;
        ultimoShotCol = col;
        conexion.enviar("SHOOT:" + fila + "," + col);
        esperandoResultado = true;

        if (gui != null) gui.onShotSent(fila, col);
        return true;
    }

    @Override
    public void onMessageReceived(String mensaje) {
        try {
            if (mensaje.startsWith("SHOOT:")) {
                String[] parts = mensaje.substring(6).split(",");
                int f = Integer.parseInt(parts[0]);
                int c = Integer.parseInt(parts[1]);

                String resultado = miTablero.disparar(f, c);
                boolean perdí = miTablero.todosHundidos();

                if (gui != null) SwingUtilities.invokeLater(() ->
                        gui.onEnemyShotReceived(f, c, resultado));

                conexion.enviar("RESULT:" + resultado + (perdí ? ";GAMEOVER" : ""));

                miTurno = !perdí;
                if (gui != null) SwingUtilities.invokeLater(() ->
                        gui.actualizarDesdeTableros(miTablero.getGrid(), tableroEnemigo.getGrid(), miTurno));

                if (perdí) {
                    gameOver = true;
                    if (gui != null) SwingUtilities.invokeLater(() -> gui.onGameOver(false));
                    conexion.enviar("QUIT");
                    conexion.close();
                }
                return;
            }

            if (mensaje.startsWith("RESULT:")) {
                String resto = mensaje.substring(7);
                boolean recibidoGameOver = resto.contains(";GAMEOVER");
                if (recibidoGameOver) resto = resto.split(";")[0];

                String resultado = resto;

                if (ultimoShotFila >= 0 && ultimoShotCol >= 0) {
                    if (resultado.equals("impacto")) tableroEnemigo.getGrid()[ultimoShotFila][ultimoShotCol] = 'X';
                    else if (resultado.equals("agua")) tableroEnemigo.getGrid()[ultimoShotFila][ultimoShotCol] = 'O';
                }

                esperandoResultado = false;
                miTurno = !resultado.equals("repetido");

                if (gui != null) SwingUtilities.invokeLater(() -> {
                    gui.onShotResult(ultimoShotFila, ultimoShotCol, resultado);
                    gui.actualizarDesdeTableros(miTablero.getGrid(), tableroEnemigo.getGrid(), miTurno);
                    if (recibidoGameOver) gui.onGameOver(true);
                });

                if (recibidoGameOver) {
                    gameOver = true;
                    conexion.close();
                }
                return;
            }

            if (mensaje.equals("QUIT")) {
                gameOver = true;
                if (gui != null) SwingUtilities.invokeLater(() -> gui.onGameOver(false));
                conexion.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionClosed() {
        if (gui != null) SwingUtilities.invokeLater(() -> gui.mostrarMensaje("Conexión cerrada."));
    }

    @Override
    public void onConnectionEstablished() {
        if (gui != null) SwingUtilities.invokeLater(() -> gui.mostrarMensaje("Conexión establecida."));
    }

    public boolean esMiTurno() {
        return miTurno && !esperandoResultado && !gameOver;
    }

    public void cerrar() {
        if (!gameOver) {
            conexion.enviar("QUIT");
            conexion.close();
            gameOver = true;
        }
    }
    public Tablero getMiTablero() {
    return miTablero;
    }

    public Tablero getTableroEnemigo() {
    return tableroEnemigo;
    }

}
