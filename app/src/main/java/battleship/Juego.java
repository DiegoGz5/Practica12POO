package battleship;

import java.util.List;
import java.util.Scanner;

public class Juego {

    private final Tablero jugador1;
    private final Tablero jugador2;

    public Juego(Tablero t1, Tablero t2) {
        this.jugador1 = t1;
        this.jugador2 = t2;
    }

    public void iniciar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Inicia la batalla naval!");

        while (true) {
            // Turno jugador 1
            System.out.println("\nTurno del Jugador 1:");
            jugador2.mostrarTableroEnemigo();
            jugador1.mostrarTableroPropio();
            turno(sc, jugador2);
            if (jugador2.todosHundidos()) {
                System.out.println("¡Jugador 1 gana!");
                break;
            }

            // Turno jugador 2
            System.out.println("\nTurno del Jugador 2:");
            jugador1.mostrarTableroEnemigo();
            jugador2.mostrarTableroPropio();
            turno(sc, jugador1);
            if (jugador1.todosHundidos()) {
                System.out.println("¡Jugador 2 gana!");
                break;
            }
        }
    }

    private void turno(Scanner sc, Tablero enemigo) {
        int fila, col;
        while (true) {
            System.out.print("Fila (0-9): ");
            fila = sc.nextInt();
            System.out.print("Columna (0-9): ");
            col = sc.nextInt();

            String resultado = enemigo.disparar(fila, col);
            if (!resultado.equals("repetido")) {
                System.out.println("Resultado: " + resultado);
                break;
            } else {
                System.out.println("Ya disparaste ahí, intenta otra posición.");
            }
        }
    }

        // Método nuevo para pruebas unitarias
    public int jugarConSecuencia(List<int[]> secuenciaJugador1, List<int[]> secuenciaJugador2) {
        int turno = 0;
        while (true) {
            int[] disparo1 = secuenciaJugador1.get(turno);
            jugador2.disparar(disparo1[0], disparo1[1]);
            if (jugador2.todosHundidos()) return 1;

            int[] disparo2 = secuenciaJugador2.get(turno);
            jugador1.disparar(disparo2[0], disparo2[1]);
            if (jugador1.todosHundidos()) return 2;

            turno++;
        }
    }
}
