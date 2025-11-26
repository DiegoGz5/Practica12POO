package battleship;

import javax.swing.*;
import java.awt.*;

public class BattleshipGUI {

    private JFrame frame;
    private JPanel panelJugador, panelEnemigo;
    private JButton[][] botonesJugador, botonesEnemigo;

    private Tablero tableroJugador1, tableroJugador2;
    private boolean turnoJugador1 = true; // true = jugador1, false = jugador2

    public BattleshipGUI(Tablero t1, Tablero t2) {
        this.tableroJugador1 = t1;
        this.tableroJugador2 = t2;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("BattleShip GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));

        panelJugador = new JPanel(new GridLayout(10, 10));
        panelEnemigo = new JPanel(new GridLayout(10, 10));

        botonesJugador = new JButton[10][10];
        botonesEnemigo = new JButton[10][10];

        // Tablero propio
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton b = new JButton("" + tableroJugador1.getGrid()[i][j]);
                b.setEnabled(false); // No se dispara en el propio tablero
                botonesJugador[i][j] = b;
                panelJugador.add(b);
            }
        }

        // Tablero enemigo
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton b = new JButton("~");
                int fila = i;
                int col = j;
                b.addActionListener(e -> {
                    Tablero jugadorActual = turnoJugador1 ? tableroJugador1 : tableroJugador2;
                    Tablero enemigo = turnoJugador1 ? tableroJugador2 : tableroJugador1;

                    // Solo dispara si es el turno correspondiente
                    if ((turnoJugador1 && jugadorActual == tableroJugador1) ||
                        (!turnoJugador1 && jugadorActual == tableroJugador2)) {

                        String resultado = enemigo.disparar(fila, col);

                        // Mostrar impacto o fallo en la interfaz
                        if (resultado.equals("impacto")) {
                            b.setText("X");
                        } else if (resultado.equals("agua")) {
                            b.setText("O");
                        }

                        // Revisar victoria
                        if (enemigo.todosHundidos()) {
                            JOptionPane.showMessageDialog(frame,
                                    "¡Jugador " + (turnoJugador1 ? "1" : "2") + " gana!");
                            frame.dispose();
                            return;
                        }

                        // Alternar turno
                        turnoJugador1 = !turnoJugador1;
                        actualizarVisibilidadTableros();
                    }
                });
                botonesEnemigo[i][j] = b;
                panelEnemigo.add(b);
            }
        }

        frame.add(panelJugador);
        frame.add(panelEnemigo);
        frame.pack();
        frame.setVisible(true);

        actualizarVisibilidadTableros();
    }

    private void actualizarVisibilidadTableros() {
        // Mostrar solo el tablero propio
        Tablero jugadorActual = turnoJugador1 ? tableroJugador1 : tableroJugador2;
        Tablero enemigo = turnoJugador1 ? tableroJugador2 : tableroJugador1;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Tablero propio
                char c = jugadorActual.getGrid()[i][j];
                botonesJugador[i][j].setText("" + c);

                // Tablero enemigo: mostrar solo X/O
                char ce = enemigo.getGrid()[i][j];
                if (ce == 'X' || ce == 'O') {
                    botonesEnemigo[i][j].setText("" + ce);
                } else {
                    botonesEnemigo[i][j].setText("~");
                }
            }
        }
    }
}
