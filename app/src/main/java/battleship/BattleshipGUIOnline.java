package battleship;

import javax.swing.*;
import java.awt.*;

public class BattleshipGUIOnline {
    private JFrame frame;
    private JPanel panelJugador, panelEnemigo;
    private JButton[][] botonesJugador, botonesEnemigo;

    private final Tablero tableroLocal; // para mostrar propio tablero (se actualiza cuando rival dispara)
    // tableroEnemigo lógico se mantiene dentro de JuegoP2P (marcado con X/O)
    private final JuegoP2P juego;

    public BattleshipGUIOnline(Tablero miTablero, JuegoP2P juego) {
        this.tableroLocal = miTablero;
        this.juego = juego;
        initialize();
        // registrar GUI en juego
        juego.setGUI(this);
    }

    private void initialize() {
        frame = new JFrame("BattleShip - Online");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));

        panelJugador = new JPanel(new GridLayout(10, 10));
        panelEnemigo = new JPanel(new GridLayout(10, 10));

        botonesJugador = new JButton[10][10];
        botonesEnemigo = new JButton[10][10];

        // Tablero propio (se muestran barcos)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton b = new JButton("" + tableroLocal.getGrid()[i][j]);
                b.setEnabled(false); // no disparas en tu tablero
                botonesJugador[i][j] = b;
                panelJugador.add(b);
            }
        }

        // Tablero enemigo (al principio ~)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton b = new JButton("~");
                final int fila = i;
                final int col = j;
                b.addActionListener(e -> {
                    // cuando se clickea una celda enemiga, delegamos en JuegoP2P
                    juego.intentarDisparo(fila, col);
                });
                botonesEnemigo[i][j] = b;
                panelEnemigo.add(b);
            }
        }

        frame.add(panelJugador);
        frame.add(panelEnemigo);
        frame.pack();
        frame.setVisible(true);
    }

    /* ---------- Métodos que el JuegoP2P llamará (callbacks) ---------- */

    // Actualiza la vista completa desde las matrices actuales
    public void actualizarDesdeTableros(char[][] miGrid, char[][] enemigoGrid, boolean miTurno) {
        // Actualizar tablero propio
        for (int i = 0; i < 10; i++) for (int j = 0; j < 10; j++)
            botonesJugador[i][j].setText("" + miGrid[i][j]);

        // Actualizar tablero enemigo (solo X/O o ~)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                char c = enemigoGrid[i][j];
                if (c == 'X' || c == 'O') botonesEnemigo[i][j].setText("" + c);
                else botonesEnemigo[i][j].setText("~");
            }
        }

        // Indicar visualmente si es tu turno (por ejemplo título)
        frame.setTitle("BattleShip - Online" + (miTurno ? " (Tu turno)" : " (Turno rival)"));
    }

    // Cuando el juego envía que se hizo un disparo (se envió)
    public void onShotSent(int fila, int col) {
        // Podrías deshabilitar temporalmente el botón hasta resultado
        botonesEnemigo[fila][col].setEnabled(false);
    }

    // Cuando llega el resultado de mi disparo
    public void onShotResult(int fila, int col, String resultado) {
        if (resultado.equals("impacto")) botonesEnemigo[fila][col].setText("X");
        else if (resultado.equals("agua")) botonesEnemigo[fila][col].setText("O");
        else if (resultado.equals("repetido")) {
            // permitir que el usuario vuelva a elegir
            botonesEnemigo[fila][col].setEnabled(true);
            mostrarMensaje("Ya habías disparado ahí, intenta otra posición.");
        }
    }

    // Cuando el rival dispara en mi tablero
    public void onEnemyShotReceived(int fila, int col, String resultado) {
        // Actualiza mi tablero visual (botonesJugador)
        botonesJugador[fila][col].setText("" + tableroLocal.getGrid()[fila][col]);
        // Mostrar mensaje corto
        mostrarMensaje("El rival disparó en (" + fila + "," + col + "): " + resultado);
    }

    public void mostrarMensaje(String msj) {
        // pequeña notificación; puedes cambiar por JOptionPane si prefieres
        System.out.println("[GUI] " + msj);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, msj));
    }

    // Aviso final de partida: gano = true si yo gané
    public void onGameOver(boolean gano) {
        String texto = gano ? "¡Ganaste!" : "Perdiste. :(";
        mostrarMensaje(texto);
        // deshabilitar todos los botones enemigos
        for (int i = 0; i < 10; i++) for (int j = 0; j < 10; j++) botonesEnemigo[i][j].setEnabled(false);
    }
}
